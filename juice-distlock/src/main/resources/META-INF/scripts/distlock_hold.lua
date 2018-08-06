local locks_key = KEYS[1]
if redis.call("get", locks_key) == ARGV[1] then
    return 1
else
    return 0
end
