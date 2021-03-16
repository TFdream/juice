local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local timestamp = tonumber(ARGV[2])
local id = ARGV[3]
local max = tonumber(ARGV[4])

-- clear out old requests that probably got lost
redis.call("zremrangebyscore", key, '-inf', max)

local count = redis.call("zcard", key)
local allowed = count < capacity

local allowed_num = 0
if allowed then
  redis.call("zadd", key, timestamp, id)
  allowed_num = 1
end

return { allowed_num, count }