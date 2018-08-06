local key = KEYS[1]
local lockValue = ARGV[1]
local milliseconds = tonumber(ARGV[4])

local allowed_num = 0
local result = redis.call("set", key, lockValue, ARGV[3], milliseconds, ARGV[2])
if result == nil then
    if redis.call("get", key) == lockValue then
        allowed_num = 1
    end
else
    allowed_num = 1
end

return allowed_num
