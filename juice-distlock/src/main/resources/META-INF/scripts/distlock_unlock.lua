local locks_key = KEYS[1]
if redis.call("get", locks_key) == ARGV[1] then
    return redis.call('del', locks_key)
else
    return 0
end
