/**
 * @(#)IDGenerator.java
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA.
 * All Rights Reserved. http://www.inwoo.co.kr
 *
 * NOTICE!    This software is the confidential and proprietary information
 * of Inwoo Tech Inc.  You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement
 * you entered into with Inwoo Tech Inc.
 */
package powerservice.common.util;

import java.rmi.RemoteException;
import java.util.LinkedList;


/**
 * IDGenerator는10의 18승 숫자인 unique한 ID를 생성해 준다. 아래의 ID generation 규칙에 의하면 앞으로 약
 * 2270년 정도까지는 유일한 숫자가 생성된다. 이 ID generation규칙은 아래에 소개된 버그를 가지고 있다. IDGenerator는
 * Thread-safe하다.
 *
 * <ol>
 * <li> 지금부터 약 2270년 이후에는 이 ID generator는 이용할 수 없다.
 * </ol>
 *
 */
public final class IDGenerator implements IIDGenerator {
    private static int s_IDCacheSize = Integer.getInteger(
        "inwoo.seed.common.util.IDGenerator.IDCacheSize", 200).intValue();

    private static LinkedList s_llIDs = new LinkedList();

    private static IIDGenerator s_IDGenerator = new IDGenerator();

    private static void fetchIDs() {
    // System.out.println("Fetch ID : " + s_IDCacheSize);
    long[] lIDs = null;
    try {
        lIDs = s_IDGenerator.getID(s_IDCacheSize);
    } catch (RemoteException re) {
        throw new RuntimeException(re.getMessage());
    }
    for (int i = 0; i < s_IDCacheSize; i++) {
        s_llIDs.add(new Long(lIDs[i]));
    }
    }

    public static void setIDGenerator(IIDGenerator idgenerator) {
    s_IDGenerator = idgenerator;
    }

    public static synchronized long generateL() {
    if (s_llIDs.size() == 0) {
        fetchIDs();
    }
    return ((Long) s_llIDs.removeFirst()).longValue();
    }

//    public static synchronized String generate() {
//        try {
//            InetAddress inetAddress = InetAddress.getLocalHost();
//            if(inetAddress.getHostAddress().equals("172.20.85.152")) {
//                return "A" + generateL();
//            } else {
//                return "B" + generateL();
//            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public static synchronized String generate() {
        return "" + generateL() + Constants.SERVERID;
    }

    public static synchronized long[] generate(int nSize) {
    if (nSize <= 0) {
        throw new IllegalArgumentException("nSize is less than zero ["
            + nSize + "]");
    }
    long[] lIDs = new long[nSize];
    for (int i = 0; i < nSize; i++) {
        lIDs[i] = generateL();
    }
    return lIDs;
    }

    /**
     * Sequence number that is used to generate the ID.
     */
    private int m_nSeq = 0;

    private long m_oldTime = System.currentTimeMillis();

    public IDGenerator() {
    }

    /**
     * This method generate the ID number. The source code that generate ID
     * is as follows. It generate the ID as sequenced order. // 2000.05.01
     *
     * old IDGenerator(2) algorithm comment .... Following simple algorithm
     * is designed to optimize the B tree family index operation(minize the
     * disk usage & reduce the tree balancing overhead). <blockquote>
     *
     * <pre>
     * public static synchronized long generateL() {
     *     return (s_nSeq++ % 90000L) * 100000000000000L + System.currentTimeMillis();
     * }
     * </pre>
     *
     * </blockquote>
     */
    public synchronized long getID() throws RemoteException {
    if (m_nSeq == 99999) {
        try {
        // 30 is rough value(rule of thumb).
        // Less than 30 may cause the problem.
        // Do not believe the Thread.sleep()
        Thread.sleep(30);
        } catch (InterruptedException e) {
        }
    }

    long lNewTime = System.currentTimeMillis();
    if (lNewTime == m_oldTime) {
        ++m_nSeq;
    } else {
        m_nSeq = 0;
    }
    m_oldTime = lNewTime;
    return (m_nSeq % 100000L) + lNewTime * 100000L;
    // return (s_nSeq++ % 90000L) * 100000000000000L +
    // System.currentTimeMillis(); // old IDgenerator(2)
    // return (s_nSeq++ % 100000L) + System.currentTimeMillis() * 100000L;
    // // old IDgenerator(1)
    }

    public long[] getID(int nSize) throws RemoteException {
    if (nSize <= 0) {
        throw new IllegalArgumentException(
            "IDGenerator's generateL() : nSize is " + nSize);
    }

    long[] arrID = new long[nSize];
    for (int i = 0; i < nSize; i++) {
        arrID[i] = getID();
    }
    return arrID;
    }

    // This is unit test class. Do not worry about this class. Just ignore
    // it.
    public static void main(String args[]) throws Exception {
    System.out.println(Long.MAX_VALUE);
    System.out.println(IDGenerator.generateL());
    System.out.println(System.currentTimeMillis());
    // for( int i = 0 ;i < 10 ; i++)
    // {
    // long[] lids = IDGenerator.generate(20);
    // for (int j = 0; j < lids.length; j++)
    // {
    // System.out.println(lids[j]);
    // }
    //
    // System.out.println(IDGenerator.generateL() );
    // }
    // for( int i = 0 ;i < 100 ; i++)
    // {
    // System.out.println(System.currentTimeMillis() );
    // try
    // {
    // Thread.sleep(10); // 30 is rough value.
    // }
    // catch(InterruptedException e) {}
    // }
    }
}