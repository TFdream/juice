local locks_key = KEYS[1]

local lockValue = ARGV[1]

if redis.call("get", locks_key) == lockValue then
    return 1
else
    return 0
end