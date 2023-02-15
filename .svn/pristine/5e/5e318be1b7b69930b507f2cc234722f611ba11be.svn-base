// ============================================================================
// 작성자 : 한경식
// 작성일자 : 2007.11.16
// 설명 : 전화상담어플리케이션의 팝업관련 Window의 속성을 설정하는 스크립트
// ============================================================================


// ============================================================================
// 화면모드 설정
// ----------------------------------------------------------------------------
//  설명 : 상담사은 2대의 모니터를 사용한다.
//         1.(M)aster  => main 모니터
//         2.(S)lave   => second 모니터
//   ┌─────┬─────┐  ┌─────┬─────┐
//   │  M1        │ M2          │  │  S1         │ S2          │
//   │               │               │  │               │               │
//   ├─────┼─────┤  ├─────┼─────┤
//   │  M3         │ M3         │  │  S3         │ S4          │
//   │               │               │  │               │               │
//   └─────┴─────┘  └─────┴─────┘
//    팝업 창의 위치를 시작 포인터는 다음과 같이 정의한다.
// ----------------------------------------------------------------------------
//    M1 : main 모니터의 (0,0) 좌표에서 시작.
//    M2 : main 모니터의 (Max/2, 0) 좌표에서 시작.
//    M3 : main 모니터의 (0, Max/2) 좌표에서 시작.
//    M4 : main 모니터의 (Max/2, Max/2) 좌표에서 시작.
//    MM : main 모니터의 화면중안 기점으로 창을 팝업시킴.
//    S1 : second 모니터의 (0,0) 좌표에서 시작.
//    S2 : second 모니터의 (Max/2, 0) 좌표에서 시작.
//    S3 : second 모니터의 (0, Max/2) 좌표에서 시작.
//    S4 : second 모니터의 (Max/2, Max/2) 좌표에서 시작.
//    SS : second 모니터의 화면중안 기점으로 창을 팝업시킴.
// ============================================================================

// ----------------------------------------------------------------------------
// 전역변수 선언 부
// ----------------------------------------------------------------------------
var moniter = new MTR();
var dafultPosition = 'SS';


//-----------------------------------------------------------------------------
// 팝업창 위치 지정
//-----------------------------------------------------------------------------
var mainPopup = 'S1';             // 메인 팝업창 설정 (주의 : 메인창은 S1,M1 둘중한개만 설정가능)


// ----------------------------------------------------------------------------
// 모니터 pupup 객체 파라미터
function MTR(){
  this.mode;
  this.position;
  this.x;
  this.y;
  this.width;
  this.height
}


function positionXY(moniter){
  if (moniter.position == "SS"){
  	moniter.x = (screen.availWidth * 0.75 ) - moniter.width/2 ;
 	moniter.y = (screen.availHeight / 2) - moniter.height/2;
  } else if (moniter.position == "S1"){
	moniter.x = screen.availWidth + 1;
 	moniter.y = 0;
  } else if (moniter.position == "S2"){
  	moniter.x = (screen.availWidth + 1024) - moniter.width-10;
 	moniter.y = 0;
  } else if (moniter.position == "S3"){
  	moniter.x = screen.availWidth + 1;
 	moniter.y = 768 - moniter.height-40;
  } else if (moniter.position == "S4"){
  	moniter.x = (screen.availWidth + 1024) - moniter.width-10;
  	moniter.y = 768 - moniter.height-40;
  } else if (moniter.position == "MM"){
  	moniter.x = (screen.availWidth * 0.25 ) - moniter.width/2 ;
 	moniter.y = (screen.availHeight / 2) - moniter.height/2;
  } else if (moniter.position == "M1"){
  	moniter.x = 0;
 	moniter.y = 0;
  } else if (moniter.position == "M2"){
  	moniter.x = screen.availWidth - moniter.width-10;
 	moniter.y = 0;
  } else if (moniter.position == "M3"){
  	moniter.x = 0;
 	moniter.y = screen.availHeight - moniter.height-40;
  } else if (moniter.position == "M4"){
 	moniter.x = screen.availWidth - moniter.width-10;
 	moniter.y = screen.availHeight - moniter.height-40;
  } else if (moniter.position == "M5"){
 	moniter.x = 215;
 	moniter.y = 140;
  } else {
	moniter.x = 0;
	moniter.y = 0;
  }
  return moniter;
}


