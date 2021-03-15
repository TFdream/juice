local name = KEYS[1]

local lockLeaseTime = tonumber(ARGV[1])
local tid = ARGV[2]

if (redis.call('exists', name) == 0) then
    redis.call('hset', name, tid, 1)
    redis.call('pexpire', name, lockLeaseTime)
    return 1;
end
if (redis.call('hexists', name, tid) == 1) then
    redis.call('pexpire', name, lockLeaseTime)
    return tonumber(redis.call('hincrby', name, tid, 1))
end
return 0