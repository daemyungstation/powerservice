/**
 * @(#)DateComparator.java
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of
 * Inwoo Tech Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Inwoo Tech Inc.
 */
package powerservice.common.util;

import java.io.File;
import java.util.Comparator;

public class DateComparator implements Comparator {
    // 상수값
    public static int DESC = 0; // 내림차순

    public static int ASC = 1; // 오른차순

    // 변수값
    private int m_nSortDirection = DESC; // 정렬방향

    /**
     * 기존 생성자
     */
    public DateComparator(int nSortDirection) {
    m_nSortDirection = nSortDirection;
    }

    /**
     * 파일을 생성된 날짜를 이용해서 정렬함
     */
    public int compare(Object o1, Object o2) {
    File file1 = (File) o1;
    File file2 = (File) o2;

    // 수정일자를 이용해서 정렬( 내림차순으로 정렬
    if (m_nSortDirection == DESC) {
        if (file1.lastModified() > file2.lastModified()) {
        return -1;
        } else if (file1.lastModified() == file2.lastModified()) {
        return 0;
        }
    } else if (m_nSortDirection == ASC) {
        if (file1.lastModified() < file2.lastModified()) {
        return -1;
        } else if (file1.lastModified() == file2.lastModified()) {
        return 0;
        }

    }

    return 1;
    }

    // -- start - entry point
    // -----------------------------------------------------
    // public static void main( String[] args)
    // {
    // File path = new File( "D:/temp/j-ajax1/Images and source");
    // File[] list = path.listFiles();
    // List tempStorage = new ArrayList();
    // for( int idx = 0; idx < list.length; idx++)
    // {
    // if( !list[ idx].isDirectory() )
    // {
    // tempStorage.add( list[ idx]);
    // }
    // }
    //
    // Collections.sort( tempStorage, new DateComparator(
    // DateComparator.DESC));
    // for( int idx = 0; idx < tempStorage.size(); idx++)
    // {
    // System.out.println(
    // ((File)tempStorage.get( idx)).getName());
    // }
    // }
}
