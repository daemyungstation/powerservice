package powerservice.common.util;

import java.net.InetAddress;
import kr.co.nicepay.module.lite.NicePayWebConnector;


public class NicePayProcess
{

    public NicePayProcess()
    {
        nicepayHome = "";  // nicepayHome 경로를 설정
        cardno = "";       //카드 번호 (-제외)
        expYear = "";      //유효기간 년도  (2자리)
        expMonth = "";     //유효기간 월 (2자리)
        cardpw = "";       //카드 비밀번호 (앞 두자리?)
        idno = "";         //식별번호(주민번호,사업자번호)
        mid = "";          //상점 ID
        merchantKey = "";  //상점 Key
        amt = "";          //금액 (결제,취소)
        billkey = "";      //빌키
        billGubun ="";     //빌구분
        goodsname = "";    //상품코드
        buyername = "";    //회원명
        buyeraddr = "";    //배송지주소
        buyertel = "";     //회원전화번호
        buyeremail = "";   //회원이메일
        cardquota = "00";  //할부개월(00 : 일시불, 01 : 1개월)
        moid = "";         //상품고유번호(주문, 취소) - 중복취소시사용
        tid = "";          //거래아이디
        cancelMsg = "";    //취소사유
        cancelPwd = "";    //취소비밀번호
        mallIP = "";       //상점고유IP
        carrier = "";      //이동통신사구분
        goodscl = "";      //상품구분
        cartArray = null;
        try
        {
            Inet = InetAddress.getLocalHost();
            mallIP = Inet.getHostAddress();
        }
        catch(Exception e)
        {
            mallIP = "127.0.0.1";
        }
    }

    public NicePayWebConnector requestCardMemAuth()
        throws Exception
    {
        NicePayWebConnector connector = new NicePayWebConnector();
        connector.setNicePayHome(nicepayHome);
        connector.addRequestData("MID", "dmlife004m");
        connector.addRequestData("EncodeKey", "Zq7E5qjYbqPMZWHvlZtt1HbC8jJIPfAkVkkGOvf7gRoKfweOMCiHu/VPob5uGrIDyYQyplxnknTXxX2D8F+emA==");
        //테스트 아이디 셋팅
        //connector.addRequestData("MID", "nictest34m");
        //connector.addRequestData("EncodeKey", "yO1t6MosWKmJxOAnD5/F50QqC5Lobf4MooTTSqqofZqbUxKKizTe5OWvbdtD8ybi3NEyK4EDxBQrgq6f3Y38Aw==");

        connector.addRequestData("MallIP", mallIP);
        connector.addRequestData("Moid", moid);
        connector.addRequestData("GoodsName", goodsname);
        connector.addRequestData("BuyerName", buyername);
        connector.addRequestData("BuyerTel", buyertel);
        connector.addRequestData("BuyerEmail", "test@test.com");
        connector.addRequestData("BuyerAddr", buyeraddr);
        connector.addRequestData("CardNo", cardno);
        connector.addRequestData("CardExpire", (new StringBuilder(String.valueOf(expYear))).append(expMonth).toString());
        connector.addRequestData("CardPwd", "");
        connector.addRequestData("BuyerAuthNum", idno);
        connector.addRequestData("Amt", "1004");
        connector.addRequestData("actionType", "PY0");
        connector.addRequestData("PayMethod", "CARD");
        connector.addRequestData("GoodsCnt", "1");
        connector.addRequestData("CardInterest", "0");
        connector.addRequestData("CardQuota", "00");
        connector.addRequestData("AuthFlg", "3");
        connector.requestAction();
        return connector;
    }

    /**
     * 	빌키 발급 요청 샘플
     *
     *
     */
    public NicePayWebConnector requestBillKey()
        throws Exception
    {
        connector = new NicePayWebConnector();
        if("".equals(nicepayHome))
            throw new Exception("NicePay Home을 설정하십시요.");
        if("".equals(cardno))
            throw new Exception("카드번호를 설정하십시요.");
        if("".equals(expYear))
            throw new Exception("유효기간 년도를 설정하십시요.");
        if("".equals(expMonth))
            throw new Exception("유효기간 월을 설정하십시요.");
        if("".equals(mid))
            throw new Exception("상점 아이디를 설정하십시요.");
        if("".equals(merchantKey))
            throw new Exception("상점키를 설정하십시요.");
        if("".equals(idno))
        {
            throw new Exception("생년월일(사업자번호)를 설정하십시오.");
        } else
        {
            connector.setNicePayHome(nicepayHome);
            connector.addRequestData("CardNo", cardno);
            connector.addRequestData("ExpYear", expYear);
            connector.addRequestData("ExpMonth", expMonth);
            connector.addRequestData("CardPw", cardpw);
            connector.addRequestData("IDNo", idno);
            connector.addRequestData("MID", mid);
            connector.addRequestData("EncodeKey", merchantKey);
            connector.addRequestData("MallIP", mallIP);
            connector.addRequestData("actionType", "PY0");
            connector.addRequestData("PayMethod", "BILLKEY");
            connector.requestAction();
            return connector;
        }
    }

    /**
     * 	key-in 결제 요청 샘플
     *
     */
    public NicePayWebConnector doPay()
        throws Exception
    {
        connector = new NicePayWebConnector();
        if("".equals(nicepayHome)) {
            throw new Exception("NicePay Home을 설정하십시요.");
        }
        if("".equals(amt)) {
            throw new Exception("상품 가격을 설정하십시요.");
        }
        if("".equals(mid)) {
            throw new Exception("상점 아이디를 설정하십시요.");
        }
        if("".equals(merchantKey)) {
            throw new Exception("상점키를 설정하십시요.");
        }
        if("".equals(cardno)) {
            throw new Exception("카드 번호를 설정하십시요.");
        } else {
            connector.setNicePayHome(nicepayHome);
            connector.addRequestData("Amt", amt);
            connector.addRequestData("MID", mid);
            connector.addRequestData("EncodeKey", merchantKey);
            connector.addRequestData("MallIP", mallIP);
            connector.addRequestData("Moid", moid);
            connector.addRequestData("GoodsName", goodsname);
            connector.addRequestData("BuyerName", buyername);
            connector.addRequestData("BuyerTel", buyertel);
            connector.addRequestData("BuyerEmail", buyeremail);
            connector.addRequestData("BuyerAddr", buyeraddr);
            connector.addRequestData("actionType", "PY0");
            connector.addRequestData("PayMethod", "CARD");
            connector.addRequestData("GoodsCnt", "1");
            connector.addRequestData("CardInterest", "0");
            connector.addRequestData("CardQuota", cardquota);
            connector.addRequestData("AuthFlg", "2");
            connector.addRequestData("CardExpire", (new StringBuilder(String.valueOf(expYear))).append(expMonth).toString());
            connector.addRequestData("CardNo", cardno);
            connector.addRequestData("CardPwd", cardpw);
            connector.addRequestData("BuyerAuthNum", idno);
            connector.requestAction();
            return connector;
        }
    }

    /**
     * 	장바구니 key-in 결제 요청 샘플
     *
     */
    public NicePayWebConnector doCartPay()
        throws Exception
    {
        connector = new NicePayWebConnector();
        if("".equals(nicepayHome))
            throw new Exception("NicePay Home을 설정하십시요.");
        if("".equals(amt))
            throw new Exception("상품 가격을 설정하십시요.");
        if("".equals(mid))
            throw new Exception("상점 아이디를 설정하십시요.");
        if("".equals(merchantKey))
            throw new Exception("상점키를 설정하십시요.");
        if("".equals(cardno))
        {
            throw new Exception("카드 번호를 설정하십시요.");
        } else
        {
            connector.setNicePayHome(nicepayHome);
            connector.addRequestData("Amt", amt);
            connector.addRequestData("MID", mid);
            connector.addRequestData("EncodeKey", merchantKey);
            connector.addRequestData("MallIP", mallIP);
            connector.addRequestData("Moid", moid);
            connector.addRequestData("GoodsName", goodsname);
            connector.addRequestData("BuyerName", buyername);
            connector.addRequestData("BuyerTel", buyertel);
            connector.addRequestData("BuyerEmail", buyeremail);
            connector.addRequestData("BuyerAddr", buyeraddr);
            connector.addRequestData("actionType", "PY0");
            connector.addRequestData("PayMethod", "CARD");
            connector.addRequestData("GoodsCnt", "1");
            connector.addRequestData("CardInterest", "0");
            connector.addRequestData("CardQuota", cardquota);
            connector.addRequestData("AuthFlg", "2");
            connector.addRequestData("CardExpire", (new StringBuilder(String.valueOf(expYear))).append(expMonth).toString());
            connector.addRequestData("CardNo", cardno);
            connector.addRequestData("CardPwd", cardpw);
            connector.addRequestData("BuyerAuthNum", idno);
            connector.addRequestData("CartData", getCartDataFormat());
            connector.addRequestData("CartCnt", Integer.toString(cartArray.length));
            connector.requestAction();
            return connector;
        }
    }

    private String getCartDataFormat()
    {
        String temp = "";
        for(int i = 0; i < cartArray.length; i++)
        {
            temp = (new StringBuilder(String.valueOf(temp))).append(cartArray[i][0]).append("`").append(cartArray[i][1]).append("`").append(cartArray[i][2]).append("`").append(cartArray[i][3]).append("`").append(cartArray[i][4]).append("`").toString();
            if(i < cartArray.length - 1)
                temp = (new StringBuilder(String.valueOf(temp))).append("|").toString();
        }

        return temp;
    }

    /**
     * 	취소 요청
     *
     */
    public NicePayWebConnector doCancel()
        throws Exception
    {
        connector = new NicePayWebConnector();
        if("".equals(nicepayHome))
            throw new Exception("NicePay Home을 설정하십시요.");
        if("".equals(amt))
            throw new Exception("상품 가격을 설정하십시요.");
        if("".equals(mid))
            throw new Exception("상점 아이디를 설정하십시요.");
        if("".equals(merchantKey))
            throw new Exception("상점키를 설정하십시요.");
        if("".equals(tid))
            throw new Exception("거래 ID를 설정하십시요.");
        if("".equals(cancelPwd))
        {
            throw new Exception("취소 비밀번호를 설정하십시요.");
        } else
        {
            connector.setNicePayHome(nicepayHome);
            connector.addRequestData("CancelAmt", amt);
            connector.addRequestData("MID", mid);
            connector.addRequestData("EncodeKey", merchantKey);
            connector.addRequestData("CancelIP", mallIP);
            connector.addRequestData("CancelMsg", cancelMsg);
            connector.addRequestData("actionType", "CL0");
            connector.addRequestData("CancelPwd", cancelPwd);
            connector.addRequestData("TID", tid);
            connector.addRequestData("PartialCancelCode", "0");
            connector.requestAction();
            return connector;
        }
    }

    /**
     * 	인증 요청
     *
     */
    public NicePayWebConnector doAuth()
        throws Exception
    {
        connector = new NicePayWebConnector();
        if("".equals(nicepayHome))
            throw new Exception("NicePay Home을 설정하십시요.");
        if("".equals(mid))

            throw new Exception("상점 아이디를 설정하십시요.");
        if("".equals(merchantKey))
            throw new Exception("상점키를 설정하십시요.");
        if("".equals(cardno))
        {
            throw new Exception("카드 번호를 설정하십시요.");
        } else
        {
            connector.setNicePayHome(nicepayHome);
            connector.addRequestData("Amt", amt);
            connector.addRequestData("MID", mid);
            connector.addRequestData("EncodeKey", merchantKey);
            connector.addRequestData("MallIP", mallIP);
            connector.addRequestData("Moid", moid);
            connector.addRequestData("GoodsName", goodsname);
            connector.addRequestData("BuyerName", buyername);
            connector.addRequestData("BuyerTel", buyertel);
            connector.addRequestData("BuyerEmail", buyeremail);
            connector.addRequestData("BuyerAddr", buyeraddr);
            connector.addRequestData("actionType", "PY0");
            connector.addRequestData("PayMethod", "CARD");
            connector.addRequestData("GoodsCnt", "1");

            // 할부 설정
            connector.addRequestData("CardInterest", "0");
            connector.addRequestData("CardQuota", cardquota);
            connector.addRequestData("AuthFlg", "3");
            connector.addRequestData("CardExpire", (new StringBuilder(String.valueOf(expYear))).append(expMonth).toString());
            connector.addRequestData("CardNo", cardno);
            connector.addRequestData("CardPwd", cardpw);
            connector.addRequestData("BuyerAuthNum", idno);
            connector.requestAction();
            return connector;
        }
    }

    /**
     * 	인증 요청
     *
     */
    public NicePayWebConnector doHppAuth()
        throws Exception
    {
        connector = new NicePayWebConnector();
        if("".equals(nicepayHome))
            throw new Exception("NicePay Home을 설정하십시요.");
        if("".equals(mid))
            throw new Exception("상점 아이디를 설정하십시요.");
        if("".equals(merchantKey))
        {
            throw new Exception("상점키를 설정하십시요.");
        } else
        {
            connector.setNicePayHome(nicepayHome);
            connector.addRequestData("Amt", "0");
            connector.addRequestData("MID", mid);
            connector.addRequestData("EncodeKey", merchantKey);
            connector.addRequestData("MallIP", mallIP);
            connector.addRequestData("Moid", moid);
            connector.addRequestData("GoodsName", "testGoods");
            connector.addRequestData("BuyerName", buyername);
            connector.addRequestData("BuyerTel", buyertel);
            connector.addRequestData("BuyerEmail", "test@test.com");
            connector.addRequestData("Carrier", carrier);
            connector.addRequestData("DstAddr", buyertel);
            connector.addRequestData("Iden", idno);
            connector.addRequestData("GoodsCl", goodscl);
            connector.addRequestData("actionType", "PY0");
            connector.addRequestData("PayMethod", "MOBILE_AUTH_NS");
            connector.addRequestData("GoodsCnt", "1");
            connector.addRequestData("MallReserved", "00");
            connector.requestAction();
            return connector;
        }
    }

    /**
     * 	빌링 결제 요청 샘플
     *
     */
    public NicePayWebConnector paybill()
        throws Exception
    {
        connector = new NicePayWebConnector();
        if("".equals(nicepayHome))
            throw new Exception("NicePay Home을 설정하십시요.");
        if("".equals(billkey))
            throw new Exception("빌키를 설정하십시요.");
        if("".equals(amt))
            throw new Exception("상품 가격을 설정하십시요.");
        if("".equals(mid))
            throw new Exception("상점 아이디를 설정하십시요.");
        if("".equals(merchantKey))
        {
            throw new Exception("상점키를 설정하십시요.");
        } else
        {
            connector.setNicePayHome(nicepayHome);
            connector.addRequestData("BillKey", billkey);
            connector.addRequestData("Amt", amt);
            connector.addRequestData("MID", mid);
            connector.addRequestData("EncodeKey", merchantKey);
            connector.addRequestData("MallIP", mallIP);
            connector.addRequestData("Moid", moid);
            connector.addRequestData("GoodsName", goodsname);
            connector.addRequestData("BuyerName", buyername);
            connector.addRequestData("actionType", "PY0");
            connector.addRequestData("PayMethod", "BILL");
            connector.addRequestData("MallReserved", billGubun);
            // 할부 설정
            //connector.addRequestData("CardInterest", "0");  // 무이자 여부  0: 일반, 1:무이자
            connector.addRequestData("CardQuota", cardquota);     // 할부 개월수 일시불 : 0
            connector.requestAction();
            return connector;
        }
    }

    public void setNicePayHome(String addr)
    {
        nicepayHome = addr;
    }

    public void setCardNumber(String no)
    {
        cardno = no;
    }

    public void setExpMonth(String month)
    {
        expMonth = month;
    }

    public void setExpYear(String year)
    {
        expYear = year;
    }

    public void setCardPw(String pw)
    {
        cardpw = pw;
    }

    public void setCardIdNo(String id)
    {
        idno = id;
    }

    public void setMID(String mid)
    {
        this.mid = mid;
    }

    public void setMerchantKey(String key)
    {
        merchantKey = key;
    }

    public void setAmt(String amt)
    {
        this.amt = amt;
    }

    public void setBillKey(String billkey)
    {
        this.billkey = billkey;
    }

    public void setMallreserved(String billGubun)
    {
        this.billGubun = billGubun;
    }



    public void setGoodsName(String goodsname)
    {
        this.goodsname = goodsname;
    }

    public void setBuyerName(String buyername)
    {
        this.buyername = buyername;
    }

    public void setBuyerTel(String buyertel)
    {
        this.buyertel = buyertel;
    }

    public void setBuyerEmail(String buyeremail)
    {
        this.buyeremail = buyeremail;
    }

    public void setBuyerAddr(String buyeraddr)
    {
        this.buyeraddr = buyeraddr;
    }

    public void setCardQuota(String cardquota)
    {
        this.cardquota = cardquota;
    }

    public void setMoid(String moid)
    {
        this.moid = moid;
    }

    public void setTid(String tid)
    {
        this.tid = tid;
    }

    public void setCancelMsg(String msg)
    {
        cancelMsg = msg;
    }

    public void setCancelPwd(String pwd)
    {
        cancelPwd = pwd;
    }

    public void setCartData(String cart[][])
    {
        cartArray = cart;
    }

    public void setTelCorp(String corp)
    {
        carrier = corp;
    }

    public void setIdno(String idno)
    {
        this.idno = idno;
    }

    public void setGoodsCl(String goodscl)
    {
        this.goodscl = goodscl;
    }

    private String nicepayHome;
    private String cardno;
    private String expYear;
    private String expMonth;
    private String cardpw;
    private String idno;
    private String mid;
    private String merchantKey;
    private String amt;
    private String billkey;
    private String billGubun;
    private String goodsname;
    private String buyername;
    private String buyeraddr;
    private String buyertel;
    private String buyeremail;
    private String cardquota;
    private String moid;
    private String tid;
    private String cancelMsg;
    private String cancelPwd;
    private String mallIP;
    private String carrier;
    private String goodscl;
    private String cartArray[][];
    NicePayWebConnector connector;
    InetAddress Inet;
}
