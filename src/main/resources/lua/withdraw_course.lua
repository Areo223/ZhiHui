-- KEYS[1]: course_stock_key (course:stock:{courseOfferingId})
-- KEYS[2]: course_students_key (course:students:{courseOfferingId})
-- ARGV[1]: studentIdentifier
-- ARGV[2]: maxCapacity

-- 先判断是否已选
if redis.call('SISMEMBER', KEYS[2], ARGV[1]) == 0 then
    return 0
end

-- 再判断判断容量是否正确
local stock = tonumber(redis.call('HGET', KEYS[1], "stock"))
if not stock then
    return 0
end

if stock >= tonumber(ARGV[2]) then
    return 0
end
-- 加库存
local newStock = redis.call('HINCRBY', KEYS[1], "stock", 1)
if newStock > tonumber(ARGV[2]) then
    redis.call('HINCRBY', KEYS[1], "stock", -1)
    return 0
end
-- 减学生
local removed = redis.call('SREM', KEYS[2], ARGV[1])
if removed == 0 then
    redis.call('HINCRBY', KEYS[1], "stock", -1)
    return 0
end
-- 成功
return 1