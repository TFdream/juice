package juice.security.util;

import juice.core.util.JsonUtils;
import juice.exceptions.InvalidTokenException;
import juice.exceptions.TokenExpiryException;
import juice.security.UserPrincipal;
import juice.security.internal.ClientType;
import juice.security.internal.JwtUserPrincipal;
import juice.util.Base64Utils;
import juice.util.CodecUtils;
import juice.util.StringUtils;

import java.time.Instant;
import java.util.Date;

/**
 * @author Ricky Fung
 */
public abstract class TokenUtils {

    //============验证token

    /**
     * 验证token
     * @param token
     * @param secret
     * @return UserPrincipal or throws InvalidTokenException & TokenExpiryException
     * @throws TokenExpiryException
     */
    public static UserPrincipal validateToken(String token, String secret) throws TokenExpiryException {
        return validateToken(token, secret, Instant.now());
    }

    /**
     * 验证token
     * @param token
     * @param secret
     * @param now
     * @return UserPrincipal or throws InvalidTokenException & TokenExpiryException
     * @throws TokenExpiryException
     */
    public static UserPrincipal validateToken(String token, String secret, Date now) throws TokenExpiryException {
        return validateToken(token, secret, now.toInstant());
    }

    /**
     * 验证token
     * @param token
     * @param secret
     * @param now
     * @return UserPrincipal or throws InvalidTokenException & TokenExpiryException
     * @throws TokenExpiryException
     */
    public static UserPrincipal validateToken(String token, String secret, Instant now) throws TokenExpiryException {
        if (StringUtils.isEmpty(token)) {
            throw new InvalidTokenException("token为空");
        }
        //1.base64解码
        String jsonToken = Base64Utils.decode(token);
        JwtUserPrincipal principal;
        try {
            principal = JsonUtils.parseObject(jsonToken, JwtUserPrincipal.class);
        } catch (Exception e) {
            throw new InvalidTokenException("token格式不正确:"+token);
        }
        //2.计算签名
        String signStr = signToken(principal, secret);
        //3.比较签名
        if(!signStr.equals(principal.getSign())){
            throw new InvalidTokenException("token签名不匹配:"+token);
        }
        if (now.isAfter(Instant.ofEpochSecond(principal.getExpiryAt()))) {
            throw new TokenExpiryException("token已过期:"+token);
        }
        return principal;
    }

    //==========生成token
    /**
     * 生成token字符串
     * @param userId
     * @param maxAge
     * @param secret
     * @param now
     * @return
     */
    public static String genTokenStr(Long userId, int maxAge, String secret, Date now) {
        return genTokenStr(userId, StringUtils.EMPTY, maxAge, ClientType.PC.getValue(), secret, now.toInstant());
    }

    public static String genTokenStr(Long userId, String secret, Instant now) {
        return genTokenStr(userId, StringUtils.EMPTY, 3600, ClientType.PC.getValue(), secret, now);
    }

    public static String genTokenStr(Long userId, int maxAge, String secret, Instant now) {
        return genTokenStr(userId, StringUtils.EMPTY, maxAge, ClientType.PC.getValue(), secret, now);
    }

    /**
     *
     * @param userId
     * @param maxAge
     * @param clientType 参考ClientType
     * @param secret
     * @param now
     * @return
     */
    public static String genTokenStr(Long userId, int maxAge,
                                     int clientType, String secret, Instant now) {
        return genTokenStr(userId, StringUtils.EMPTY, maxAge, clientType, secret, now);
    }

    public static String genTokenStr(Long userId, String name, int maxAge,
                                     int clientType, String secret, Instant now) {
        UserPrincipal tokenInfo = genToken(userId, name, maxAge, clientType, secret, now);
        String json = JsonUtils.toJson(tokenInfo);
        return Base64Utils.encode(json);
    }

    /**
     * 生成token对象
     * @param userId
     * @param maxAge
     * @param secret
     * @param now
     * @return
     */
    public static UserPrincipal genToken(Long userId, int maxAge, String secret, Instant now) {
        return genToken(userId, StringUtils.EMPTY, maxAge, ClientType.PC.getValue(), secret, now);
    }

    /**
     *
     * @param userId
     * @param name
     * @param maxAge
     * @param clientType 参考ClientType
     * @param secret
     * @param now
     * @return
     */
    public static UserPrincipal genToken(Long userId, String name, int maxAge,
                                         int clientType, String secret, Instant now) {
        JwtUserPrincipal principal = new JwtUserPrincipal();
        principal.setUserId(userId);
        principal.setNickname(name);
        principal.setIssueAt(now.getEpochSecond());
        principal.setExpiryAt(principal.getIssueAt() + maxAge);
        principal.setClientType(clientType);
        principal.setSign(signToken(principal, secret));
        return principal;
    }

    //------------

    /**
     * token信息签名
     * @param principal
     * @param secret
     * @return
     */
    private static String signToken(JwtUserPrincipal principal, String secret) {
        String signStr = String.format("%s#%s#%s#%s#%s#%s", principal.getUserId(), principal.getNickname(),
                principal.getIssueAt(), principal.getExpiryAt(), principal.getClientType(), secret);
        return CodecUtils.sha1Hex(signStr);
    }

}
