
if (redis.call('hexists', KEYS[1], ARGV[2]) == 0) then
    return -1
end
local counter = tonumber(redis.call('hincrby', KEYS[1], ARGV[2], -1))
if (counter > 0) then
    redis.call('pexpire', KEYS[1], ARGV[1])
    return counter
else
    redis.call('del', KEYS[1])
    return 1
end