/**
 * @(#)XMLDOMHandler.java
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA.
 * All Rights Reserved. http://www.inwoo.co.kr
 *
 * NOTICE!    This software is the confidential and proprietary information
 * of Inwoo Tech Inc.  You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement
 * you entered into with Inwoo Tech Inc.
 */
package powerservice.core.util.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.XPathAPI;

/**
 * XML 파일, 스트림, 문자열 등을 읽어들여 DOM을 생성한후, 생성된 DOM을 통해 원하는 정보를
 * 접근하기 위한 기능 제공
 *
 * Copyright (c) 2011
 * Company		Inwoo tech inc.
 *
 * @author		문규원
 * @version		1.0
 * @date		2013/04/01
 */
public class ParseDOM
{
	private DocumentBuilderFactory m_DocBuilderFactory = null;
	private DocumentBuilder        m_DocBuilder        = null;
	private Document               m_Document          = null; // XML DOM 객체

	/**
	 * 파일 경로(파일명 포함) 를 받아들여서, 해당 정보에 대한 DOM 객체를 생성
	 *
	 * @throws ParseDOMException 새로운 DOM 객체 생성도중 발생한 예외
	 */
	public ParseDOM()
		throws Exception
	{
		try
		{
			m_DocBuilderFactory = DocumentBuilderFactory.newInstance();
			m_DocBuilder        = m_DocBuilderFactory.newDocumentBuilder();
		}
		catch( ParserConfigurationException except)
		{
			String szMessage = "XMLDOMHandler() fail...."; // 메시지 프로퍼티로 변경 예정

			throw new Exception( szMessage, except);
		}
	}

	/**
	 * 파일 경로(파일명 포함) 를 받아들여서, 해당 정보에 대한 DOM 객체를 생성
	 *
	 * @param  filename XML 정보를 담고 있는 파일의 경로( 파일명 포함 )
	 * @return XML 정보의 파싱여부
	 * @throws Exception Document 객체 생성도중 발생한 예외
	 */
	public boolean loading( String filename)
		throws Exception
	{
		boolean result = false;

	// filename의 contents 를 읽어서, DOM 정보를 생성함
		try
		{
			m_Document = m_DocBuilder.parse( filename);
			if( m_Document != null )
			{
				result = true;
			}
		}
		catch( Exception except)
		{
			String szMessage = "loading( String filename) fail...."; // 메시지 프로퍼티로 변경 예정

			throw new Exception( szMessage, except);
		}

		return result;
	}

	/**
	 * 파일 경로(파일명 포함) 를 받아들여서, 해당 정보에 대한 DOM 객체를 생성
	 *
	 * @param  inputStream XML 정보를 담고 있는 입력객체
	 * @return XML 정보의 파싱여부
	 * @throws Exception Document 객체 생성도중 발생한 예외
	 */
	public boolean loading( InputStream inputStream)
		throws Exception
	{
		boolean result = false;

	// inputstream의  contents 를 읽어서, DOM 정보를 생성함
		try
		{
			m_Document = m_DocBuilder.parse( inputStream);
			if( m_Document != null )
			{
				result = true;
			}
		}
		catch( Exception except)
		{
			String szMessage = "loading( InputStream inputStream) fail...."; // 메시지 프로퍼티로 변경 예정

			throw new Exception( szMessage, except);
		}

		return result;
	}

	/**
	 * 해당 경로에 위치한 노드를 반환
	 *
	 * @param  path DOM 내의 XML 정보 경로
	 * @return 해당 경로의 노드
	 * @throws Exception XPath 를 이용한 검색 도중 발생한 예외
	 */
	public Node getNode( String path)
		throws Exception
	{
		try
		{
			return XPathAPI.selectSingleNode( m_Document, path);
		}
		catch( TransformerException transformerExcept)
		{
			String szMessage = "getNode( String path) fail...."; // 메시지 프로퍼티로 변경 예정

			throw new Exception( szMessage, transformerExcept);
		}
	}

	/**
	 * 해당 경로에 위치한 노드를 반환
	 *
	 * @param  parentNode 검색을 시작할 노드
	 * @param  path parentNode 내의 XML 정보 경로
	 * @return 해당 경로의 노드
	 * @throws Exception XPath 를 이용한 검색 도중 발생한 예외
	 */
	public Node getNode( Node parentNode, String path)
		throws Exception
	{
		try
		{
			return XPathAPI.selectSingleNode( parentNode, path);
		}
		catch( TransformerException transformerExcept)
		{
			String szMessage = "getNode( Node parentNode, String path) fail...."; // 메시지 프로퍼티로 변경 예정

			throw new Exception( szMessage, transformerExcept);
		}
	}

	/**
	 * 해당 경로에 위치한 노드들을 반환함
	 *
	 * @param  parentNode 검색을 시작할 노드
	 * @param  path DOM 내의 XML 정보 경로
	 * @return 해당 경로의 노드드르
	 * @throws Exception XPath 를 이용한 검색 도중 발생한 예외
	 */
	public NodeList getNodeList( Node parentNode, String path)
		throws Exception
	{
		try
		{
			return XPathAPI.selectNodeList( parentNode, path);
		}
		catch( TransformerException transformerExcept)
		{
			String szMessage = "getNodeList( Node parentNode, String path) fail...."; // 메시지 프로퍼티로 변경 예정

			throw new Exception( szMessage, transformerExcept);
		}
	}

	/**
	 * 해당 경로에 위치한 노드들을 반환함
	 *
	 * @param path DOM 내의 XML 정보 경로
	 * @return 해당 경로의 노드드르
	 * @throws Exception XPath 를 이용한 검색 도중 발생한 예외
	 */
	public NodeList getNodeList( String path )
		throws Exception
	{
		try
		{
			return XPathAPI.selectNodeList( m_Document, path);
		}
		catch( TransformerException transformerExcept)
		{
			String szMessage = "getNodeList( String path ) fail...."; // 메시지 프로퍼티로 변경 예정
			throw new Exception( szMessage, transformerExcept);
		}
	}

	/**
	 * 노드의 값을 반환함
	 *
	 * @param node 값을 가진 노드 객체
	 * @return node의 값
	 * @throws Exception 주어진 노드에 대한 값을 검색하는 도중 예외발생
	 */
	public String getNodeValue( Node node)
		throws Exception
	{
		if( node != null && node.getFirstChild() != null)
		{
			return node.getFirstChild().getNodeValue();
		}

		String szMessage = "getNodeValue( Node node) fail...."; // 메시지 프로퍼티로 변경 예정
		throw new Exception( szMessage);
	}

	/**
	 * 노드의 값을 반환함
	 *
	 * @param node		값을 가진 노드 객체
	 * @param szDefault node가 널이거나 값이 없는 경우의 값
	 * @return node의 값
	 */
	public String getNodeValue( Node node, String szDefault)
	{
		if( node != null && node.getFirstChild() != null)
		{
			return node.getFirstChild().getNodeValue();
		}

		return szDefault;
	}

	/**
	 * 전역객체들을 소멸함
	 */
	public void destroy()
	{
		m_DocBuilderFactory = null;
		m_DocBuilder 		= null;
		m_Document          = null;
	}
}