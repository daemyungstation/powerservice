/**
 * 고객 정보
 */
var Cust = function() {
    this.page_indx = ko.observable();

    // 고객 정보
    this.cust_id = ko.observable();
    this.cust_nm = ko.observable();
    this.cust_typ_cd = ko.observable();
    this.cust_div_cd = ko.observable();
    this.cust_tend_cd = ko.observable();
    this.zpcd = ko.observable().extend({zipCdFormat: true});
    this.addr_olnw_yn = ko.observable();
    this.basi_addr = ko.observable();
    this.dtpt_addr = ko.observable();
    this.tlno = ko.observable().extend({phoneNumberFormat: true});
    this.clph_no = ko.observable().extend({phoneNumberFormat: true});
    this.fax_no = ko.observable().extend({phoneNumberFormat: true});
    this.emil_addr = ko.observable();
    this.unfc_csno = ko.observable();
    this.hmpg_id = ko.observable();
    this.cust_idnt_no = ko.observable();
    this.sns_id = ko.observable();
    this.fist_cons_cd = ko.observable();
    this.fist_cons_dtls_cd = ko.observable();
    this.cust_brdt = ko.observable().extend({momentFormat: "YYYYMMDD"});
    this.slcn_lunr_div_cd = ko.observable("10");
    this.gndr_cd = ko.observable();
    this.ntfr_div_cd = ko.observable();
    this.atwr_nm = ko.observable();
    this.atwr_tlno = ko.observable().extend({phoneNumberFormat: true});
    this.atwr_addr_olnw_yn = ko.observable();
    this.atwr_basi_addr = ko.observable();
    this.atwr_dtpt_addr = ko.observable();
    this.atwr_zpcd = ko.observable().extend({zipCdFormat: true});
    this.rppr_nm = ko.observable();
    this.bztp_cd = ko.observable();
    this.biz_itms_cd = ko.observable();
    this.bzst_cd = ko.observable();
    this.cntr_cd = ko.observable();
    this.rgsr_id = ko.observable();
    this.rgsn_dttm = ko.observable();
    this.amnd_id = ko.observable();
    this.amnt_dttm = ko.observable();

    this.rgsr_nm = ko.observable();
    this.amnd_nm = ko.observable();
    this.cust_tend_nm = ko.observable();
    this.cust_div_nm = ko.observable();
    this.cust_typ_nm = ko.observable();
    this.fist_cons_nm = ko.observable();
    this.fist_cons_dtls_nm = ko.observable();
    this.gndr_nm = ko.observable();
    this.ntfr_div_nm = ko.observable();
    this.slcn_lunr_div_nm = ko.observable();
    this.bztp_nm = ko.observable();
    this.biz_itms_nm = ko.observable();
    this.bzst_nm = ko.observable();

    // 개인 정보 활용 동의
    this.prif_gthr_cnsn_yn_bool = ko.observable();
    this.prif_gthr_cnsn_yn = ko.observable().extend({checkbox: {boolTarget: this.prif_gthr_cnsn_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.prif_thpr_offr_cnsn_yn_bool = ko.observable();
    this.prif_thpr_offr_cnsn_yn = ko.observable().extend({checkbox: {boolTarget: this.prif_thpr_offr_cnsn_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.ents_offr_cnsn_yn_bool = ko.observable();
    this.ents_offr_cnsn_yn = ko.observable().extend({checkbox: {boolTarget: this.ents_offr_cnsn_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.prif_mrkn_pcus_cnsn_yn_bool = ko.observable();
    this.prif_mrkn_pcus_cnsn_yn = ko.observable().extend({checkbox: {boolTarget: this.prif_mrkn_pcus_cnsn_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.dm_pcus_cnsn_yn_bool = ko.observable();
    this.dm_pcus_cnsn_yn = ko.observable().extend({checkbox: {boolTarget: this.dm_pcus_cnsn_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.emil_pcus_cnsn_yn_bool = ko.observable();
    this.emil_pcus_cnsn_yn = ko.observable().extend({checkbox: {boolTarget: this.emil_pcus_cnsn_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.tm_pcus_cnsn_yn_bool = ko.observable();
    this.tm_pcus_cnsn_yn = ko.observable().extend({checkbox: {boolTarget: this.tm_pcus_cnsn_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.prif_gthr_cnsn_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});
    this.prif_thpr_offr_cnsn_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});
    this.ents_offr_cnsn_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});
    this.prif_mrkn_pcus_cnsn_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});
    this.prif_mrkn_pcus_vld_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});

    this.anonymous = ko.observable(false);
    this.anonymous.subscribe(function(pbChecked) {
        this.cust_nm(pbChecked ? "익명" : "");
    }.bind(this));

    this.prif_gthr_cnsn_dt.subscribe(function (newValue) {
        if (newValue != null && newValue != "") {
            this.prif_gthr_cnsn_yn_bool(true);
        } else {
            this.prif_gthr_cnsn_yn_bool(false);
        }
    }.bind(this));

    this.prif_thpr_offr_cnsn_dt.subscribe(function (newValue) {
        if (newValue != null && newValue != "") {
            this.prif_thpr_offr_cnsn_yn_bool(true);
        } else {
            this.prif_thpr_offr_cnsn_yn_bool(false);
        }
    }.bind(this));

    this.ents_offr_cnsn_dt.subscribe(function (newValue) {
        if (newValue != null && newValue != "") {
            this.ents_offr_cnsn_yn_bool(true);
        } else {
            this.ents_offr_cnsn_yn_bool(false);
        }
    }.bind(this));

    this.prif_mrkn_pcus_cnsn_dt.subscribe(function (newValue) {
        if (newValue != null && newValue != "") {
            this.prif_mrkn_pcus_cnsn_yn_bool(true);
        } else if (this.prif_mrkn_pcus_vld_dt() == "" || this.prif_mrkn_pcus_vld_dt() == null){
            this.prif_mrkn_pcus_cnsn_yn_bool(false);
        }
    }.bind(this));

    this.prif_mrkn_pcus_vld_dt.subscribe(function (newValue) {
        if (newValue != null && newValue != "") {
            this.prif_mrkn_pcus_cnsn_yn_bool(true);
        } else if (this.prif_mrkn_pcus_cnsn_dt() == "" || this.prif_mrkn_pcus_cnsn_dt() == null){
            this.prif_mrkn_pcus_cnsn_yn_bool(false);
        }
    }.bind(this));

    // 고객특이메모
    this.cust_unus_memo_cnt = ko.observable();
};


/**
 * 상담 정보
 */
var Cons = function() {
    this.page_indx = ko.observable();

    this.consno = ko.observable();
    this.consno_grop_no = ko.observable();
    this.consno_sqno = ko.observable();

    // 상담접수속성
    this.ars_srvc_addr = ko.observable();
    this.inco_tlno = ko.observable().extend({phoneNumberFormat: true});
    this.call_id = ko.observable();
    this.cust_id = ko.observable();
    this.acpg_chnl_cd = ko.observable("10"); // 접수채널 (default : 전화)
    this.acpg_chnl_dtpt_cd = ko.observable();
    this.acpg_dttm = ko.observable();
    this.actp_id = ko.observable();

    // 상담처리속성
    this.totl_cons_hr = ko.observable();
    this.acpg_trgt_cd = ko.observable();
    this.clnt_nm = ko.observable();
    this.clnt_cntc_tlno = ko.observable().extend({phoneNumberFormat: true});
    this.clnt_cust_rltn_cd = ko.observable();
    this.cons_typ1_cd = ko.observable();
    this.cons_typ2_cd = ko.observable();
    this.cons_typ3_cd = ko.observable();
    this.cons_qust_cntn = ko.observable();
    this.cons_ansr_cntn = ko.observable();
    this.cons_stat_cd = ko.observable("30"); // 처리상태 (default : 처리완료)
    this.old_cons_dspsmddl_dtpt_cd = ko.observable();
    this.cons_dspsmddl_dtpt_cd = ko.observable("10"); // 처리방법 (default : 일반상담)
    this.cons_dsps_dttm = ko.observable();
    this.rsps_dept_cd = ko.observable();
    this.chpr_id = ko.observable();
    this.trct_box_id = ko.observable();
    this.trct_sqno = ko.observable();

    // 관리자수정속성
    this.admr_amnt_yn = ko.observable();
    this.admr_id = ko.observable();
    this.admr_amnt_dttm = ko.observable();

    // 재통화예약속성
    this.recrnc_cntc_tlno = ko.observable().extend({phoneNumberFormat: true});
    this.recrnc_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});
    this.recrnc_hrmn = ko.observable().extend({momentFormat: "HHmm"});
    this.recrnc_dttm = ko.observable().extend({momentFormat: "YYYYMMDDHHmmss"});
    this.sido_tmcnt = ko.observable(0);
    this.cnsr_id = ko.observable();
    this.recrnc_sido_dttm = ko.observable();

    // 이관속성
    this.trct_sqno = ko.observable();
    this.trct_box_nm = ko.observable();
    this.cons_atmt_cmpl_yn = ko.observable();
    this.bswr_dmnd_acpg_cntn = ko.observable();

    this.trct_typ_cd = ko.observable();
    this.trct_typ_nm = ko.observable();
    this.bswr_dmnd_stat_cd = ko.observable();
    this.old_bswr_dmnd_stat_cd = ko.observable();
    this.bswr_dmnd_stat_nm = ko.observable();
    this.trct_actp_id = ko.observable();
    this.trct_actp_nm = ko.observable();
    this.trct_acpg_dttm = ko.observable();
    this.trct_chpr_id = ko.observable();
    this.trct_chpr_nm = ko.observable();
    this.trct_chpr_dept_nm = ko.observable();
    this.bswr_dmnd_cnft_dttm = ko.observable();
    this.trnm_dsps_dttm = ko.observable();
    this.calb_resr_term = ko.observable();
    this.bswr_dmnd_dsps_cntn = ko.observable();
    this.trct_cons_sctr_cd = ko.observable();
    this.trct_cons_sctr_nm = ko.observable();
    this.trct_trgt_div_cd = ko.observable();
    this.trct_trgt_div_nm = ko.observable();
    this.trct_cons_cstr_nm = ko.observable();
    this.trct_acpg_dept_cd = ko.observable();
    this.trct_acpg_dept_nm = ko.observable();
    this.trct_cons_atmt_cmpl_yn_bool = ko.observable();
    this.trct_cons_atmt_cmpl_yn=ko.observable().extend({checkbox: {boolTarget: this.trct_cons_atmt_cmpl_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.trct_chpr_fg = ko.observable();

    // Audit속성
    this.cntr_cd = ko.observable();
    this.rgsr_id = ko.observable();
    this.rgsr_nm = ko.observable();
    this.rgsn_dttm = ko.observable();
    this.amnd_id = ko.observable();
    this.amnt_dttm = ko.observable();

    // 상위유형 초기화시 하위유형도 초기화
    this.cons_typ1_cd.subscribe(function(newValue) {
        if (newValue == "") {
            this.cons_typ2_cd("");
        }
    }.bind(this));
    this.cons_typ2_cd.subscribe(function(newValue) {
        if (newValue == "") {
            this.cons_typ3_cd("");
        }
    }.bind(this));

    this.cons_typ1_nm = ko.observable();
    this.cons_typ2_nm = ko.observable();
    this.cons_typ3_nm = ko.observable();
    this.actp_nm = ko.observable();
    this.chpr_nm = ko.observable();
    this.amnd_nm = ko.observable();
    this.actp_team1_cd = ko.observable();
    this.actp_team2_cd = ko.observable();
    this.actp_team1_nm = ko.observable();
    this.actp_team2_nm = ko.observable();
    this.ars_srvc_addr_nm = ko.observable();
    this.acpg_chnl_nm = ko.observable("I/B");
    this.acpg_chnl_dtpt_nm = ko.observable();
    this.acpg_trgt_nm = ko.observable();
    this.clnt_cust_rltn_nm = ko.observable();
    this.cons_stat_nm = ko.observable();
    this.cons_dspsmddl_dtpt_nm = ko.observable();

    // 기타
    this.today_flag = ko.observable();
    this.recrnc_left_tm = ko.observable();
    this.recrnc_term = ko.observable();


    // 고객 정보
    this.cust_nm = ko.observable();
    this.cust_typ_cd = ko.observable();
    this.cust_div_cd = ko.observable();
    this.cust_tend_cd = ko.observable();
    this.zpcd = ko.observable().extend({zipCdFormat: true});
    this.addr_olnw_yn = ko.observable();
    this.basi_addr = ko.observable();
    this.dtpt_addr = ko.observable();
    this.tlno = ko.observable().extend({phoneNumberFormat: true});
    this.clph_no = ko.observable().extend({phoneNumberFormat: true});
    this.fax_no = ko.observable().extend({phoneNumberFormat: true});
    this.emil_addr = ko.observable();
    this.unfc_csno = ko.observable();
    this.hmpg_id = ko.observable();
    this.cust_idnt_no = ko.observable();
    this.sns_id = ko.observable();
    this.fist_cons_cd = ko.observable();
    this.fist_cons_dtls_cd = ko.observable();
    this.cust_brdt = ko.observable().extend({momentFormat: "YYYYMMDD"});
    this.slcn_lunr_div_cd = ko.observable("10");
    this.gndr_cd = ko.observable();
    this.ntfr_div_cd = ko.observable();
    this.atwr_nm = ko.observable();
    this.atwr_tlno = ko.observable().extend({phoneNumberFormat: true});
    this.atwr_addr_olnw_yn = ko.observable();
    this.atwr_basi_addr = ko.observable();
    this.atwr_dtpt_addr = ko.observable();
    this.atwr_zpcd = ko.observable().extend({zipCdFormat: true});
    this.rppr_nm = ko.observable();
    this.bztp_cd = ko.observable();
    this.biz_itms_cd = ko.observable();
    this.bzst_cd = ko.observable();

    this.cust_tend_nm = ko.observable();
    this.cust_div_nm = ko.observable();
    this.cust_typ_nm = ko.observable();
    this.fist_cons_nm = ko.observable();
    this.fist_cons_dtls_nm = ko.observable();
    this.gndr_nm = ko.observable();
    this.ntfr_div_nm = ko.observable();
    this.slcn_lunr_div_nm = ko.observable();
    this.bztp_nm = ko.observable();
    this.biz_itms_nm = ko.observable();
    this.bzst_nm = ko.observable();
};


/**
 * VOC
 */
var VocDtl = function() {
    var self = this;

    this.page_indx = ko.observable();
    this.voc_id = ko.observable();

    // 접수속성
    this.voc_acpg_chnl_cd = ko.observable();
    this.voc_acpg_chnl_nm = ko.observable();
    this.cust_id = ko.observable();
    this.voc_cust_nm = ko.observable();
    this.consno = ko.observable();
    this.voc_typ_cd = ko.observable();
    this.voc_typ_nm = ko.observable();
    this.voc_impc_cd = ko.observable();
    this.voc_impc_nm = ko.observable();
    this.voc_titl = ko.observable();
    this.voc_acpg_cntn = ko.observable();
    this.sms_rpl_yn_bool = ko.observable(false);
    this.sms_rpl_yn = ko.observable().extend({checkbox: {boolTarget: self.sms_rpl_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.sms_rpl_cnpl_tlno = ko.observable().extend({phoneNumberFormat: true});

    this.tlph_rpl_yn_bool = ko.observable(false);
    this.tlph_rpl_yn = ko.observable().extend({checkbox: {boolTarget: self.tlph_rpl_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.tlph_rpl_cnpl_tlno = ko.observable().extend({phoneNumberFormat: true});

    this.emil_rpl_yn_bool = ko.observable(false);
    this.emil_rpl_yn = ko.observable().extend({checkbox: {boolTarget: self.emil_rpl_yn_bool, checkedVal: "Y", uncheckedVal: "N"}});
    this.emil_rpl_addr = ko.observable();

    this.sms_rpl_cnpl_tlno.subscribe(function(newVal1) {
        if(newVal1 !== "" && newVal1 !== null){
            this.sms_rpl_yn_bool(true);
        }else{
            this.sms_rpl_yn_bool(false);
        }
    }.bind(this));

    this.tlph_rpl_cnpl_tlno.subscribe(function(newVal2){
        if(newVal2 !== "" && newVal2 !== null){
            this.tlph_rpl_yn_bool(true);
        }else{
            this.tlph_rpl_yn_bool(false);
        }
    }.bind(this));

    this.emil_rpl_addr.subscribe(function(newVal3){
        if(newVal3 !== "" && newVal3 !== null){
            this.emil_rpl_yn_bool(true);
        }else{
            this.emil_rpl_yn_bool(false);
        }
    }.bind(this));

    // 처리속성
    this.voc_dsps_stat_cd = ko.observable();
    this.voc_dsps_stat_nm = ko.observable();
    this.voc_dsps_cntn = ko.observable();
    this.voc_actp_id = ko.observable();
    this.voc_actp_nm = ko.observable();
    this.voc_acpg_dttm = ko.observable();
    this.voc_dssr_id = ko.observable();
    this.voc_dssr_nm = ko.observable();
    this.voc_dsps_dttm = ko.observable();
    this.voc_dsps_term = ko.observable();
    this.voc_dssr_team1_cd = ko.observable();
    this.voc_dssr_team1_nm = ko.observable();
    this.voc_dssr_team2_cd = ko.observable();
    this.voc_dssr_team2_nm = ko.observable();

    // 해피콜속성
    this.hpcl_trgt_yn = ko.observable("N");
    this.hpcl_sido_tmcnt = ko.observable(0);
    this.hpcl_dssr_id = ko.observable();
    this.hpcl_dssr_nm = ko.observable();
    this.hpcl_dsps_dttm = ko.observable();
    this.hpcl_dsps_stat_cd = ko.observable();
    this.hpcl_dsps_stat_nm = ko.observable();

    // Audit속성
    this.cntr_cd = ko.observable();
    this.rgsr_id = ko.observable();
    this.rgsr_nm = ko.observable();
    this.rgsn_dttm = ko.observable();
    this.amnd_id = ko.observable();
    this.amnd_nm = ko.observable();
    this.amnt_dttm = ko.observable();

    // 기타
    this.voc_dsps_term = ko.observable();

    // 상위유형 초기화시 하위유형도 초기화
    this.voc_dssr_team1_cd.subscribe(function(newValue) {
        if (newValue == "") {
            this.voc_dssr_team2_cd("");
        }
    }.bind(this));
    this.voc_dssr_team2_cd.subscribe(function(newValue) {
        if (newValue == "") {
            this.voc_dssr_id("");
        }
    }.bind(this));
};
