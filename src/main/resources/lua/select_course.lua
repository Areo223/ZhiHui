-- KEYS[1]: course_stock_key (course:stock:{courseOfferingId})
-- KEYS[2]: course_students_key (course:students:{courseOfferingId})
-- ARGV[1]: studentIdentifier
-- ARGV[2]: maxCapacity

-- 检查是否已选
if redis.call('SISMEMBER', KEYS[2], ARGV[1]) == 1 then
    return 0
end

-- 检查库存
local stock = tonumber(redis.call('HGET', KEYS[1], "stock"))
if not stock or stock <= 0 then
    return 0
end

-- 原子性操作: 减库存和添加学生
redis.call('HINCRBY', KEYS[1], "stock", -1)
local added = redis.call('SADD', KEYS[2], ARGV[1])

-- 如果添加失败(理论上不应该发生)，回滚库存
if added == 0 then
    redis.call('HINCRBY', KEYS[1], "stock", 1)
    return 0
end

return 1