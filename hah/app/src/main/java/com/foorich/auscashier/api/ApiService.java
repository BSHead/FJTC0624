package com.foorich.auscashier.api;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-6 8:44
 * desc   :
 * version: 1.0
 */
public interface ApiService {



    //   测试环境     "http://192.168.5.242:9121"
    //   雅文环境     "http://192.168.2.130:9121"

    /**
     * 服务器地址
     */
    public static String ServiceAddress = "http://192.168.5.242:9121";
    /**
     * 注册
     */
    public final static String Register = "/app/user/addUser.do";
    /**
     * 验证短信验证码
     */
    public final static String Checkmessage = "/app/user/checkmessage.do";
    /**
     * 发送短信验证码
     */
    public final static String Mdsmssend = "/app/user/mdsmssend.do";
    /**
     * 用户修改密码
     */
    public final static String UpdateUserPassword = "/app/user/updateUserPassword.do";
    /**
     * 验证原密码
     */
    public final static String chekUserPassword = "/app/user/chekUserPassword.do";
    /**
     * 登录
     */
    public final static String Login = "/app/user/login.do";
    /**
     *  上传图片
     */
    public final static String upload = "/appmerchant/file/upload";
    /**
     *  更新头像
     */
    public final static String doorPhoto = "/appmerchant/merchant/doorPhoto";
    /**
     *  设置支付密码、忘记支付密码
     */
    public final static String updatePayPassword = "/app/user/updateUserPayPassword.do";
    /**
     *  验证原支付密码
     */
    public final static String chekUserPay = "/app/user/chekUserPayPassword.do";

    /**
     *  商户开通
     */
    public final static String MerchantBase = "/appmerchant/merchant/saveOrUpdateMerchantBase";
    /**
     *  获取商户经营类型
     */
    public final static String getAllMerchant = "/appmerchant/merchant/getAllMerchant";
    /**
     *  获取所在地区
     */
    public final static String getAllAreasApp = "/appmerchant/merchant/getAllAreasApp";
    /**
     *  保存页面信息
     */
    public final static String saveOrUpdateMerchantBase = "/appmerchant/merchant/saveOrUpdateMerchantBase";
    /**
     *  商户资质
     */
    public final static String saveOrUpdateMerchantZZ = "/appmerchant/merchant/saveOrUpdateMerchantZZ";
    /**
     *  分享好友
     */
    public final static String ShareUrl = "/appmerchant/share/generateUrl";
    /**
     *  获取开户银行
     */
    public final static String getBankList = "/appmerchant/bank/getBankList";
    /**
     *  保存银行卡信息
     */
    public final static String saveOrUpdateBank = "/appmerchant/merchant/saveOrUpdateBank";
    /**
     *  上传门店照门店照照片
     */
    public final static String saveOrUpdatePhoto = "/appmerchant/merchant/saveOrUpdatePhoto";
    /**
     *  查询申请商户审核状态
     */
    public final static String getByMerchantCode = "/appmerchant/merchant/getByMerchantCode";
    /**
     *  修改商户昵称
     */
    public final static String nickname = "/appmerchant/merchant/nickname";
    /**
     *  获取商户收款静态码
     */
    public final static String QrcodeTokenUser = "/app/qrcodeTokenUser/getQrcodeTokenUser.do";
    /**
     *  商户扫一扫
     */
    public final static String doPay = "/app/pay/doPay.do";
    /**
     *  商户轮训查询订单
     */
    public final static String getOrder = "/app/order/getOrder.do";
    /**
     *  首页初始化
     */
    public final static String homepage = "/appmerchant/homepage/main";
    /**
     *  流水查询
     */
    public final static String dealflow = "/appmerchant/dealflow/queryGroupOrderList.do";
    /**
     *  退款
     */
    public final static String refund = "appmerchant/refund/passwordChecking.do";
    /**
     *  消息推送
     */
    public final static String getMessage = "/appmerchant/messageExpress/getMessage";
    /**
     *  消息推送状态更新
     */
    public final static String updateFlag = "/appmerchant/messageExpress/updateFlag";
    /**
     *  查询系统公告和消息公告是否有未读消息
     */
    public final static String getMessageNum = "/appmerchant/messageExpress/getMessageNum";
    /**
     *  收入报表
     */
    public final static String incomeStatement = "/appmerchant/data/incomeStatement";
    /**
     *  订单报表
     */
    public final static String orderStatement = "/appmerchant/data/orderStatement";
    /**
     *  支付方式报表-饼图
     */
    public final static String payMent = "/appmerchant/data/payMent";


}
