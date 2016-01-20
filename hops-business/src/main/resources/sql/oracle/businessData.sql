-- 8 records loaded
-- Loading CARRIER_INFO...
insert into CARRIER_INFO (CARRIER_NO, CARRIER_NAME, CARRIER_TYPE, UPDATE_USER, UPDATE_TIME, STATUS, CREATE_TIME)
values ('YD', '移动', 1, null, to_date('21-10-2013 11:44:56', 'dd-mm-yyyy hh24:mi:ss'), '0', to_date('21-10-2013', 'dd-mm-yyyy'));
insert into CARRIER_INFO (CARRIER_NO, CARRIER_NAME, CARRIER_TYPE, UPDATE_USER, UPDATE_TIME, STATUS, CREATE_TIME)
values ('LT', '联通', 1, null, to_date('21-10-2013 11:44:57', 'dd-mm-yyyy hh24:mi:ss'), '0', to_date('21-10-2013', 'dd-mm-yyyy'));
insert into CARRIER_INFO (CARRIER_NO, CARRIER_NAME, CARRIER_TYPE, UPDATE_USER, UPDATE_TIME, STATUS, CREATE_TIME)
values ('DX', '电信', 1, null, to_date('21-10-2013 11:44:57', 'dd-mm-yyyy hh24:mi:ss'), '0', to_date('21-10-2013', 'dd-mm-yyyy'));
commit;
-- 3 records loaded
-- Loading CITY...
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('010_', '北京', 0, 'BJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('020_', '广州', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('021_', '上海', 0, 'SH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('022_', '天津', 0, 'TJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('023_', '重庆', 0, 'CQ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('024_', '沈阳', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('025_', '南京', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('027_', '武汉', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('028_', '成都', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0281', '眉山', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('029_', '西安', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0310', '邯郸', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0311', '石家庄', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0312', '保定', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0313', '张家口', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0314', '承德', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0315', '唐山', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0316', '廊坊', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0317', '沧州', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0318', '衡水', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0319', '邢台', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0335', '秦皇岛', 0, 'HEB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0349', '朔州', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0350', '忻州', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0351', '太原', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0352', '大同', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0353', '阳泉', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0354', '晋中', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0355', '长治', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0356', '晋城', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0357', '临汾', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0358', '吕梁地区', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0359', '运城', 0, 'SX2');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0370', '商丘', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0371', '郑州', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0372', '安阳', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0373', '新乡', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0374', '许昌', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0375', '平顶山', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0376', '信阳', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0377', '南阳', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0378', '开封', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0379', '洛阳', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0391', '焦作', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0392', '鹤壁', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0393', '濮阳', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0394', '周口', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0395', '漯河', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0396', '驻马店', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0398', '三门峡', 0, 'HEN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0410', '铁岭', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0411', '大连', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0412', '鞍山', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0413', '抚顺', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0414', '本溪', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0415', '丹东', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0416', '锦州', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0417', '营口', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0418', '阜新', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0419', '辽阳', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0421', '朝阳', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0427', '盘锦', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0429', '葫芦岛', 0, 'LN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0431', '长春', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0432', '吉林', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0433', '延边朝鲜族自治州', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0434', '四平', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0435', '通化', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0436', '白城', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0437', '辽源', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0438', '松原', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0439', '白山', 0, 'JN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0451', '哈尔滨', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0452', '齐齐哈尔', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0453', '牡丹江', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0454', '佳木斯', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0455', '绥化', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0456', '黑河', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0457', '大兴安岭地区', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0458', '伊春', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0459', '大庆', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0464', '七台河', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0467', '鸡西', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0468', '鹤岗', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0469', '双鸭山', 0, 'HLJ');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0470', '呼伦贝尔', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0471', '呼和浩特', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0472', '包头', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0473', '乌海', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0474', '乌兰察布盟', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0475', '通辽', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0476', '赤峰', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0477', '鄂尔多斯', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0478', '巴彦淖尔盟', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0479', '锡林郭勒盟', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0482', '兴安盟', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0483', '阿拉善盟', 0, 'NMG');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0510', '无锡', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0511', '镇江', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0512', '苏州', 0, 'JS_');
commit;
-- 100 records committed...
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0513', '南通', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0514', '扬州', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0515', '盐城', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0516', '徐州', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0517', '淮安', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0518', '连云港', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0519', '常州', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0523', '泰州', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0527', '宿迁', 0, 'JS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0530', '菏泽', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0531', '济南', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0532', '青岛', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0533', '淄博', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0534', '德州', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0535', '烟台', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0536', '潍坊', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0537', '济宁', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0538', '泰安', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0539', '临沂', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0543', '滨州', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0546', '东营', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0550', '滁州', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0551', '合肥', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0552', '蚌埠', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0553', '芜湖', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0554', '淮南', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0555', '马鞍山', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0556', '安庆', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0557', '宿州', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0558', '亳州', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0559', '黄山', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0561', '淮北', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0562', '铜陵', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0563', '宣城', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0564', '六安', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0565', '巢湖', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0566', '池州', 0, 'AH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0570', '衢州', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0571', '杭州', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0572', '湖州', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0573', '嘉兴', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0574', '宁波', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0575', '绍兴', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0576', '台州', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0577', '温州', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0578', '丽水', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0579', '金华', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0580', '舟山', 0, 'ZJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0591', '福州', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0592', '厦门', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0593', '宁德', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0594', '莆田', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0595', '泉州', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0596', '漳州', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0597', '龙岩', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0598', '三明', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0599', '南平', 0, 'FJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0631', '威海', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0632', '枣庄', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0633', '日照', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0634', '莱芜', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0635', '聊城', 0, 'SD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0660', '汕尾', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0662', '阳江', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0663', '揭阳', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0668', '茂名', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0691', '西双版纳傣族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0692', '德宏傣族景颇族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0701', '鹰潭', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0710', '襄樊', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0711', '鄂州', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0712', '孝感', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0713', '黄冈', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0714', '黄石', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0715', '咸宁', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0716', '荆州', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0717', '宜昌', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0718', '恩施', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0719', '十堰', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0722', '随州', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0724', '荆门', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0728', '天门', 0, 'HUB');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0730', '岳阳', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0731', '长沙', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0732', '湘潭', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0733', '株洲', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0734', '衡阳', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0735', '郴州', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0736', '常德', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0737', '益阳', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0738', '娄底', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0739', '邵阳', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0743', '湘西土家族苗族自治州', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0744', '张家界', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0745', '怀化', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0746', '永州', 0, 'HUN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0750', '江门', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0751', '韶关', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0752', '惠州', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0753', '梅州', 0, 'GD_');
commit;
-- 200 records committed...
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0754', '汕头', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0755', '深圳', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0756', '珠海', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0757', '佛山', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0758', '肇庆', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0759', '湛江', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0760', '中山', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0762', '河源', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0763', '清远', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0766', '云浮', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0768', '潮州', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0769', '东莞', 0, 'GD_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0770', '防城港', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0771', '南宁', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0772', '柳州', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0773', '桂林', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0774', '梧州', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0775', '玉林', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0776', '百色地区', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0777', '钦州', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0778', '河池地区', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0779', '北海', 0, 'GX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0790', '新余', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0791', '南昌', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0792', '九江', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0793', '上饶', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0794', '抚州', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0795', '宜春', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0796', '吉安', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0797', '赣州', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0798', '景德镇', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0799', '萍乡', 0, 'JX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0812', '攀枝花', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0813', '自贡', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0816', '绵阳', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0817', '南充', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0818', '达州', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0825', '遂宁', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0826', '广安', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0827', '巴中', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0830', '泸州', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0831', '宜宾', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0832', '内江', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0833', '乐山', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0834', '凉山', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0835', '雅安', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0836', '甘孜', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0837', '阿坝', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0838', '德阳', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0839', '广元', 0, 'SC_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0851', '贵阳', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0852', '遵义', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0853', '安顺', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0854', '黔南布依族苗族自治州', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0855', '黔东南苗族侗族自治州', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0856', '铜仁地区', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0857', '毕节地区', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0858', '六盘水', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0859', '黔西南布依族苗族自治州', 0, 'GZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0870', '昭通', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0871', '昆明', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0872', '大理白族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0873', '红河哈尼族彝族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0874', '曲靖', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0875', '保山', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0876', '文山壮族苗族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0877', '玉溪', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0878', '楚雄彝族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0879', '思茅地区', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0883', '临沧地区', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0886', '怒江傈傈族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0887', '迪庆藏族自治州', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0888', '丽江地区', 0, 'YN_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0891', '拉萨', 0, 'XZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0892', '日喀则地区', 0, 'XZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0893', '山南地区', 0, 'XZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0894', '林芝地区', 0, 'XZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0895', '昌都地区', 0, 'XZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0896', '那曲地区', 0, 'XZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0897', '阿里地区', 0, 'XZ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0898', '海口', 0, 'HIN');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0901', '塔城地区', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0902', '哈密地区', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0903', '和田地区', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0906', '阿勒泰地区', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0908', '克孜勒苏柯尔克孜自治州', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0909', '博尔塔拉蒙古自治州', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0911', '延安', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0912', '榆林', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0913', '渭南', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0914', '商洛', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0915', '安康', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0916', '汉中', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0917', '宝鸡', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0919', '铜川', 0, 'SX1');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0930', '临夏回族自治州', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0931', '兰州', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0932', '定西地区', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0933', '平凉地区', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0934', '庆阳地区', 0, 'GS_');
commit;
-- 300 records committed...
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0935', '金昌', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0936', '张掖地区', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0937', '酒泉地区', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0938', '天水', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0939', '陇南地区', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0941', '甘南藏族自治州', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0943', '白银', 0, 'GS_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0951', '银川', 0, 'NX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0952', '石嘴山', 0, 'NX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0953', '吴忠', 0, 'NX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0954', '固原', 0, 'NX_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0970', '海北藏族自治州', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0971', '西宁', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0972', '海东地区', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0973', '黄南藏族自治州', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0974', '海南藏族自治州', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0975', '果洛藏族自治州', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0976', '玉树藏族自治州', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0977', '海西蒙古族藏族自治州', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0979', '格尔木', 0, 'QH_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0990', '克拉玛依', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0991', '乌鲁木齐', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0992', '奎屯', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0993', '石河子', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0994', '昌吉回族自治州', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0995', '吐鲁番地区', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0996', '巴音郭楞蒙古自治州', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0997', '阿克苏地区', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0998', '喀什地区', 0, 'XJ_');
insert into CITY (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0999', '伊犁哈萨克自治州', 0, 'XJ_');
commit;
-- 330 records loaded
-- Loading ERROR_CODE...
insert into ERROR_CODE (CODE, MSG)
values ('302', '绑定订单失败');
insert into ERROR_CODE (CODE, MSG)
values ('101', '订单充值中');
insert into ERROR_CODE (CODE, MSG)
values ('102', '充值失败');
insert into ERROR_CODE (CODE, MSG)
values ('103', '充值成功');
insert into ERROR_CODE (CODE, MSG)
values ('271', '发送订单频率过快');
insert into ERROR_CODE (CODE, MSG)
values ('261', '充值产品和账号不匹配');
insert into ERROR_CODE (CODE, MSG)
values ('260', '系统不支持该产品');
insert into ERROR_CODE (CODE, MSG)
values ('259', '渠道订单规则未设置');
insert into ERROR_CODE (CODE, MSG)
values ('258', '订单不存在');
insert into ERROR_CODE (CODE, MSG)
values ('257', '加解密错误');
insert into ERROR_CODE (CODE, MSG)
values ('256', '渠道产品未开通');
insert into ERROR_CODE (CODE, MSG)
values ('255', '购买数量非法');
insert into ERROR_CODE (CODE, MSG)
values ('254', '金额错误');
insert into ERROR_CODE (CODE, MSG)
values ('253', '商家不存在或未启用');
insert into ERROR_CODE (CODE, MSG)
values ('252', '订单已存在');
insert into ERROR_CODE (CODE, MSG)
values ('251', '签名错误');
insert into ERROR_CODE (CODE, MSG)
values ('250', '必须参数为空');
insert into ERROR_CODE (CODE, MSG)
values ('200', '对不起，操作失败，请重试');
insert into ERROR_CODE (CODE, MSG)
values ('100', '恭喜，操作成功');
insert into ERROR_CODE (CODE, MSG)
values ('272', '提交订单总金额超过限制金额');
insert into ERROR_CODE (CODE, MSG)
values ('601', '商家扣款失败');
insert into ERROR_CODE (CODE, MSG)
values ('999', '充值异常，需要人工审核');
insert into ERROR_CODE (CODE, MSG)
values ('0101', '缺少必须参数');
insert into ERROR_CODE (CODE, MSG)
values ('0102', '签名失败');
insert into ERROR_CODE (CODE, MSG)
values ('0103', '参数有误，参数格式或值非法');
insert into ERROR_CODE (CODE, MSG)
values ('0104', '找不到对应订单');
insert into ERROR_CODE (CODE, MSG)
values ('0301', '订单重复');
insert into ERROR_CODE (CODE, MSG)
values ('0302', '购买数量非法');
insert into ERROR_CODE (CODE, MSG)
values ('0303', '商品库存不足');
insert into ERROR_CODE (CODE, MSG)
values ('0304', '商品维护中');
insert into ERROR_CODE (CODE, MSG)
values ('0305', '找不到对应的商品');
insert into ERROR_CODE (CODE, MSG)
values ('0306', '参数有误，被充值帐户有误，比如联通充值时输入了移动的号码，QQ充值时输入字母');
insert into ERROR_CODE (CODE, MSG)
values ('0501', '运营商例行维护，无法充值');
insert into ERROR_CODE (CODE, MSG)
values ('0502', '被充值帐户有误，比如联通充值时输入了移动的号码，QQ充值时输入字母');
insert into ERROR_CODE (CODE, MSG)
values ('0503', '充值失败');
insert into ERROR_CODE (CODE, MSG)
values ('0701', '非法的订单状态转换');
insert into ERROR_CODE (CODE, MSG)
values ('0901', '充值超时，订单被取消');
insert into ERROR_CODE (CODE, MSG)
values ('9999', '未知错误');
commit;

-- 16 records loaded
-- Loading NUM_SECTION...
insert into NUM_SECTION (SECTION_ID, CARRIER_NO, PROVINCE_ID, CITY_ID, MOBILE_TYPE, PRIORITY, USED_TIMES, CREATE_TIME)
values ('1363731', 'YD', 'HUN', '0731', '全球通卡', 0, 0, to_date('31-10-2014 13:50:26', 'dd-mm-yyyy hh24:mi:ss'));
insert into NUM_SECTION (SECTION_ID, CARRIER_NO, PROVINCE_ID, CITY_ID, MOBILE_TYPE, PRIORITY, USED_TIMES, CREATE_TIME)
values ('1587400', 'YD', 'HUN', '0731', '移动全球通卡', 0, 0, to_date('16-07-2014 14:59:04', 'dd-mm-yyyy hh24:mi:ss'));
insert into NUM_SECTION (SECTION_ID, CARRIER_NO, PROVINCE_ID, CITY_ID, MOBILE_TYPE, PRIORITY, USED_TIMES, CREATE_TIME)
values ('1511635', 'YD', 'HUN', '0731', '移动151卡', 0, 0, to_date('14-07-2014 15:37:26', 'dd-mm-yyyy hh24:mi:ss'));
insert into NUM_SECTION (SECTION_ID, CARRIER_NO, PROVINCE_ID, CITY_ID, MOBILE_TYPE, PRIORITY, USED_TIMES, CREATE_TIME)
values ('1375517', 'YD', 'HUN', '0731', '移动全球通卡', 0, 0, to_date('19-08-2014 17:19:41', 'dd-mm-yyyy hh24:mi:ss'));
commit;
-- 4 records loaded
-- Loading PARAMETER_CONFIGURATION... 
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (25000, '1000', 'scanning_orders', null, null, null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (10001, '5', 'manual_audit_order', 's', '秒', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (26000, '10', 'repeat_binding_times', null, null, null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (29000, '10', 'random_binding_times', null, null, null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (29002, '1', 'bind_interval_time', 'm', '绑定间隔时间(分钟)', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (10002, '2', 'time_out_order', 'h', '时', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (26001, '5', 'delivery_times', null, null, null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (27000, '3', 'notify_max_times', null, null, null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (29001, '3', 'delivery_interval_time', 'm', '发货间隔时间(分钟)', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (29003, '10', 'polling_interval_time', 'm', '轮询间隔时间(分钟)', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (10003, '15', 'scanner_bind_num', null, '定时任务，轮循绑定记录条数', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (10004, '15', 'scanner_send_num', null, '定时任务，轮循发货记录条数', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (10005, '15', 'scanner_query_num', null, '定时任务，轮循查询记录条数', null, null);
insert into PARAMETER_CONFIGURATION (ID, CONSTANT_VALUE, CONSTANT_NAME, CONSTANT_UNIT_VALUE, CONSTANT_UNIT_NAME, EXT1, EXT2)
values (10006, '15', 'scanner_notify_num', null, '定时任务，轮循通知记录条数', null, null);
commit;
-- 10 records loaded
-- Loading PRODUCT_IDENTITY_RELATION...
-- Table is empty
-- Loading PRODUCT_OPERATION_DETAIL...
insert into PRODUCT_OPERATION_DETAIL (ID, PRODUCT_OPERATION_HISTORY_ID, PRODUCT_RELATION_ID, PRODUCT_RELATION_STATUS, MERCHANT_TYPE)
values (45000, 47000, 45011, '0', 'SUPPLY');
insert into PRODUCT_OPERATION_DETAIL (ID, PRODUCT_OPERATION_HISTORY_ID, PRODUCT_RELATION_ID, PRODUCT_RELATION_STATUS, MERCHANT_TYPE)
values (45001, 47000, 45012, '0', 'SUPPLY');
insert into PRODUCT_OPERATION_DETAIL (ID, PRODUCT_OPERATION_HISTORY_ID, PRODUCT_RELATION_ID, PRODUCT_RELATION_STATUS, MERCHANT_TYPE)
values (45002, 47000, 45010, '0', 'SUPPLY');
commit;


-- 1 records loaded
-- Loading PRODUCT_PROPERTY...
insert into PRODUCT_PROPERTY (PRODUCT_PROPERTY_ID, PARAM_NAME, ATTRIBUTE, MIN_LENGTH, MAX_LENGTH, VALUE, PARAM_ENGLISH_NAME)
values (110002, '运营商', 'select', null, null, null, 'carrierName');
insert into PRODUCT_PROPERTY (PRODUCT_PROPERTY_ID, PARAM_NAME, ATTRIBUTE, MIN_LENGTH, MAX_LENGTH, VALUE, PARAM_ENGLISH_NAME)
values (110003, '省份', 'select', null, null, null, 'province');
insert into PRODUCT_PROPERTY (PRODUCT_PROPERTY_ID, PARAM_NAME, ATTRIBUTE, MIN_LENGTH, MAX_LENGTH, VALUE, PARAM_ENGLISH_NAME)
values (110000, '城市', 'select', null, null, null, 'city');
insert into PRODUCT_PROPERTY (PRODUCT_PROPERTY_ID, PARAM_NAME, ATTRIBUTE, MIN_LENGTH, MAX_LENGTH, VALUE, PARAM_ENGLISH_NAME)
values (110001, '面值', 'word', 0, 10, null, 'parValue');
commit;

insert into PRODUCT_TYPE (TYPE_ID, PRODUCT_TYPE_NAME, PRODUCT_TYPE_STATUS)
values (114002, '产品类型3(运省城面)', '1');
insert into PRODUCT_TYPE (TYPE_ID, PRODUCT_TYPE_NAME, PRODUCT_TYPE_STATUS)
values (114000, '产品类型1(运面)', '1');
insert into PRODUCT_TYPE (TYPE_ID, PRODUCT_TYPE_NAME, PRODUCT_TYPE_STATUS)
values (114001, '产品类型2(运省面)', '1');

commit;
-- 3 records loaded
-- Loading PRODUCT_TYPE_RELATION...
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116000, 110002, 114000);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116001, 110001, 114000);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116002, 110002, 114001);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116003, 110003, 114001);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116004, 110001, 114001);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116005, 110002, 114002);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116006, 110003, 114002);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116007, 110000, 114002);
insert into PRODUCT_TYPE_RELATION (ID, PRODUCT_PROPERTY_ID, TYPE_ID)
values (116008, 110001, 114002);
commit;
-- 9 records loaded
-- Loading PROVINCE...
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (1, 'AH_', '安徽', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (2, 'BJ_', '北京', 0, 2);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (3, 'CQ_', '重庆', 0, 5);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (4, 'FJ_', '福建', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (5, 'GD_', '广东', 0, 9);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (6, 'GS_', '甘肃', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (7, 'GX_', '广西', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (8, 'GZ_', '贵州', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (9, 'HEB', '河北', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (10, 'HEN', '河南', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (11, 'HIN', '海南', 0, 6);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (12, 'HLJ', '黑龙江', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (13, 'HUB', '湖北', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (14, 'HUN', '湖南', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (15, 'JN_', '吉林', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (16, 'JS_', '江苏', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (17, 'JX_', '江西', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (18, 'LN_', '辽宁', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (19, 'NMG', '内蒙古', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (20, 'NX_', '宁夏', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (21, 'QG_', '全国', 0, 1);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (22, 'QH_', '青海', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (23, 'SC_', '四川', 0, 4);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (24, 'SD_', '山东', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (25, 'SH_', '上海', 0, 3);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (26, 'SX1', '陕西', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (27, 'SX2', '山西', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (28, 'TJ_', '天津', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (29, 'XJ_', '新疆', 0, 7);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (30, 'XZ_', '西藏', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (31, 'YN_', '云南', 0, 10);
insert into PROVINCE (ID, PROVINCE_ID, PROVINCE_NAME, STATUS, SORT_ID)
values (32, 'ZJ_', '浙江', 0, 8);
commit;

-- 淘宝错误码
-- Loading RESPONSE_CODE_TRANSLATION...
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (1, 'reciever_order', '100', 'true', '100', '操作成功');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (2, 'reciever_order', '200', 'false', '200', '对不起，操作失败，请重试');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (3, 'reciever_order', '250', 'false', '250', '必须参数为空');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (4, 'reciever_order', '251', 'false', '251', '签名错误');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (5, 'reciever_order', '252', 'false', '252', '订单已存在');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (6, 'reciever_order', '253', 'false', '253', '商家不存在');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (7, 'reciever_order', '254', 'false', '254', '订单金额错误');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (8, 'reciever_order', '255', 'false', '255', '购买产品数量非法');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (9, 'reciever_order', '256', 'false', '256', '产品未开通或关闭');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (10, 'reciever_order', '260', 'false', '260', '系统不支持该产品');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (11, 'reciever_order', '261', 'false', '261', '手机号码鉴权失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (12, 'reciever_order', '272', 'false', '272', '订单总金额超过限制金额');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (13, 'reciever_order', '601', 'false', '601', '扣款失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (14, 'reciever_order', '999', 'false', '999', '操作失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (15, 'reciever_Tborder', '100', 'UNDERWAY', null, '充值中');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (16, 'reciever_Tborder', '200', 'CANCEL', '0901', '充值超时，订单被取消');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (17, 'reciever_Tborder', '250', 'REQUEST_FAILED', '0101', '缺少必需参数');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (18, 'reciever_Tborder', '251', 'REQUEST_FAILED', '0102', '签名失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (19, 'reciever_Tborder', '252', 'REQUEST_FAILED', '0301', '订单重复');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (20, 'reciever_Tborder', '253', 'ORDER_FAILED', '0501', '运营商例行维护，无法充值');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (21, 'reciever_Tborder', '254', 'ORDER_FAILED', '0503', '充值失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (22, 'reciever_Tborder', '255', 'ORDER_FAILED', '0302', '购买产品数量非法');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (23, 'reciever_Tborder', '256', 'ORDER_FAILED', '0304', '商品维护中');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (24, 'reciever_Tborder', '260', 'ORDER_FAILED', '0305', '找不到对应的商品');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (25, 'reciever_Tborder', '261', 'ORDER_FAILED', '0306', '参数有误，被充值帐户有误，比如联通充值时输入了移动的号码，QQ充值时输入字母');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (26, 'reciever_Tborder', '272', 'ORDER_FAILED', '0503', '充值失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (27, 'reciever_Tborder', '601', 'ORDER_FAILED', '0503', '充值失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (28, 'reciever_Tborder', '999', 'ORDER_FAILED', '0503', '充值失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (29, 'agent_query_order', '100', 'true', '100', '操作成功');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (30, 'agent_query_order', '101', 'true', '101', '查单成功，充值中');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (31, 'agent_query_order', '102', 'true', '102', '查单成功，充值失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (32, 'agent_query_order', '103', 'true', '103', '查单成功，充值成功');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (33, 'agent_query_order', '200', 'false', '200', '对不起，操作失败，请重试');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (34, 'agent_query_order', '250', 'false', '250', '必须参数为空');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (35, 'agent_query_order', '251', 'false', '251', '签名错误');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (36, 'agent_query_order', '253', 'false', '253', '商家不存在');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (37, 'agent_query_order', '258', 'false', '258', '订单不存在');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (38, 'agent_query_order', '999', 'false', '999', '操作失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (39, 'agent_query_TBorder', '100', 'UNDERWAY', null, '充值中');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (40, 'agent_query_TBorder', '101', 'UNDERWAY', null, '充值中');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (41, 'agent_query_TBorder', '102', 'FAILED', '0503', '充值失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (42, 'agent_query_TBorder', '103', 'SUCCESS', null, '充值成功');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (43, 'agent_query_TBorder', '200', 'ORDER_FAILED', '0503', '充值超时，订单被取消');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (44, 'agent_query_TBorder', '250', 'REQUEST_FAILED', '0101', '缺少必需参数');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (45, 'agent_query_TBorder', '251', 'REQUEST_FAILED', '0102', '签名失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (46, 'agent_query_TBorder', '253', 'REQUEST_FAILED', '0501', '运营商例行维护，无法充值');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (47, 'agent_query_TBorder', '258', 'REQUEST_FAILED', '0104', '找不到对应的订单');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (48, 'agent_query_TBorder', '999', 'ORDER_FAILED', '0503', '充值失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (49, 'reciever_order', 'account000001', 'false', '601', '扣款失败');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (51, 'reciever_order', '104', 'false', '104', '下单未知错误，请查询获取结果');
insert into RESPONSE_CODE_TRANSLATION (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (52, 'reciever_Tborder', '104', 'ORDER_FAILED', '0503', '充值失败');
commit;


insert into URI_TRANSACTION_MAPPING (ID, HOST_IP, HOST_PORT, ACTION_NAME, MERCHANT_ID, INTERFACE_TYPE, TRANSACTION_CODE)
values (1, '*', '*', '/recieverOrder', null, 'reciever_order', '100000');
insert into URI_TRANSACTION_MAPPING (ID, HOST_IP, HOST_PORT, ACTION_NAME, MERCHANT_ID, INTERFACE_TYPE, TRANSACTION_CODE)
values (2, '*', '*', '/queryOrder', null, 'agent_query_order', '100001');
insert into uri_transaction_mapping (ID, HOST_IP, HOST_PORT, ACTION_NAME, MERCHANT_ID, INTERFACE_TYPE, TRANSACTION_CODE)
values (3, '*', '*', '/cmpayNotify', null, 'supply_notify_order', '100002');
commit;


insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34028, 65001, 1, 'string', 'transParam', 'merchantOrderNo', 'oid', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34029, 65001, 2, 'string', 'transParam', 'merchantCode', 'cid', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34030, 65001, 3, 'string', 'transParam', 'productFace', 'pr', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34031, 65001, 4, 'string', 'transParam', 'productNum', 'nb', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34032, 65001, 5, 'string', 'transParam', 'orderFee', 'fm', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34033, 65001, 6, 'string', 'transParam', 'notifyUrl', 'ru', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34034, 65001, 7, 'string', 'transParam', 'userCode', 'pn', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34035, 65001, 8, 'date', 'date', 'orderRequestTime', 'tsp', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34036, 65001, 9, 'string', 'transParam', 'sign', 'sign', 'MD5', '(oid)(cid)(pr)(nb)(fm)(pn)(ru)(tsp)(key)', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34038, 65001, 2, 'string', 'transParam', 'result', 'response.result', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34037, 65001, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34039, 65001, 3, 'string', 'transParam', 'errorCode', 'response.code', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34040, 65001, 4, 'string', 'transParam', 'msg', 'response.msg', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135001, 65007, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135002, 65007, 2, 'string', 'transParam', 'result', 'response.result', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135003, 65007, 3, 'string', 'transParam', 'errorCode', 'response.code', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135004, 65007, 4, 'string', 'transParam', 'msg', 'response.msg', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135005, 65007, 6, 'string', 'transParam', 'orderNo', 'response.data.sid', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135006, 65007, 7, 'string', 'transParam', 'orderStatus', 'response.data.ste', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135007, 65007, 8, 'string', 'transParam', 'merchantCode', 'response.data.cid', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135008, 65007, 9, 'string', 'transParam', 'productNo', 'response.data.pid', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135009, 65007, 10, 'string', 'transParam', 'merchantOrderNo', 'response.data.oid', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135010, 65007, 11, 'string', 'transParam', 'userCode', 'response.data.pn', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135011, 65007, 12, 'string', 'transParam', 'orderFee', 'response.data.fm', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135012, 65007, 13, 'string', 'transParam', 'info1', 'response.data.info1', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135013, 65007, 14, 'string', 'transParam', 'info2', 'response.data.info2', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135014, 65007, 15, 'string', 'transParam', 'info3', 'response.data.info3', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135015, 65007, 16, 'string', 'transParam', 'orderRequestTime', 'response.data.ft', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135016, 65007, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135017, 65007, 2, 'string', 'transParam', 'result', 'response.result', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135018, 65007, 3, 'string', 'transParam', 'errorCode', 'response.code', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135019, 65007, 4, 'string', 'transParam', 'msg', 'response.msg', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135020, 65010, 2, 'string', 'transParam', 'merchantOrderNo', 'tbOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135021, 65010, 3, 'string', 'transParam', 'orderNo', 'coopOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135022, 65010, 4, 'string', 'transParam', 'msg', 'coopOrderStatus', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135023, 65010, 5, 'string', 'transParam', 'orderDesc', 'coopOrderSnap', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135024, 65010, 6, 'string', 'transParam', 'orderFinishTime', 'coopOrderSuccessTime', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34151, 65004, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34152, 65004, 2, 'string', 'transParam', 'merchantOrderNo', 'response.tbOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34153, 65004, 3, 'string', 'transParam', 'orderNo', 'response.coopOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34154, 65004, 4, 'string', 'transParam', 'msg', 'response.coopOrderStatus', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34155, 65004, 5, 'string', 'transParam', 'orderDesc', 'response.coopOrderSnap', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34156, 65004, 6, 'date', 'date', 'orderRequestTime', 'response.coopOrderSuccessTime', 'NO_NEED', '', 'response', 'true', 'unchanged', 'yyyyMMddHHmmss', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34256, 65004, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34257, 65004, 2, 'string', 'transParam', 'merchantOrderNo', 'response.tbOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34258, 65004, 3, 'string', 'transParam', 'orderNo', 'response.coopOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34259, 65004, 4, 'string', 'transParam', 'msg', 'response.coopOrderStatus', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34260, 65004, 5, 'string', 'transParam', 'errorCode', 'response.failedCode', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34262, 65004, 6, 'string', 'transParam', 'closeReason', 'response.failedReason', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135028, 65010, 3, 'string', 'transParam', 'orderNo', 'coopOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135029, 65010, 2, 'string', 'transParam', 'merchantOrderNo', 'tbOrderNo', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135030, 65010, 4, 'string', 'transParam', 'orderDesc', 'coopOrderSnap', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135031, 65010, 6, 'string', 'transParam', 'closeReason', 'failedReason', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135032, 65010, 5, 'string', 'transParam', 'error_code', 'failedCode', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34041, 65001, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34042, 65001, 2, 'string', 'transParam', 'result', 'response.result', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34043, 65001, 3, 'string', 'transParam', 'errorCode', 'response.code', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34044, 65001, 4, 'string', 'transParam', 'msg', 'response.msg', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34046, 65001, 6, 'string', 'transParam', 'merchantOrderNo', 'response.data.sid', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34142, 65004, 1, 'string', 'transParam', 'merchantId', 'coopId', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34143, 65004, 2, 'string', 'transParam', 'merchantOrderNo', 'tbOrderNo', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34144, 65004, 3, 'string', 'transParam', 'productNo', 'cardId', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34145, 65004, 4, 'string', 'transParam', 'number', 'cardNum', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34146, 65004, 5, 'string', 'transParam', 'userCode', 'customer', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34147, 65004, 6, 'string', 'transParam', 'orderFee', 'sum', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34148, 65004, 7, 'string', 'transParam', 'orderDesc', 'tbOrderSnap', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34149, 65004, 8, 'string', 'transParam', 'notifyUrl', 'notifyUrl', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (34150, 65004, 9, 'string', 'transParam', 'sign', 'sign', 'MD5', 'cardId(cardId)cardNum(cardNum)coopId(coopId)customer(customer)notifyUrl(notifyUrl)sum(sum)tbOrderNo(tbOrderNo)tbOrderSnap(tbOrderSnap)(key)', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (36151, 65007, 1, 'string', 'transParam', 'merchantOrderNo', 'oid', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (36152, 65007, 2, 'string', 'transParam', 'merchantCode', 'cid', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (36153, 65007, 3, 'date', 'date', 'orderRequestTime', 'tsp', 'NO_NEED', '', 'request', 'true', 'unchanged', 'yyyyMMddHHmmss', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (36154, 65007, 4, 'string', 'transParam', 'sign', 'sign', 'MD5', '(oid)(cid)(tsp)(key)', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (36155, 65010, 1, 'string', 'transParam', 'merchantCode', 'coopId', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (36156, 65010, 2, 'string', 'transParam', 'merchantOrderNo', 'tbOrderNo', 'NO_NEED', '', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (36157, 65010, 3, 'string', 'transParam', 'sign', 'sign', 'MD5', '(coopId)(tbOrderNo)(key)', 'request', 'true', 'unchanged', '', '');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135034, 65010, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'success');

insert into interface_param (ID, INTERFACE_DEFINITION_ID, SEQUENCE, DATA_TYPE, PARAM_TYPE, INPUT_PARAM_NAME, OUT_PARAM_NAME, ENCRYPTION_FUNCTION, ENCRYPTION_PARAM_NAMES, CONNECTION_MODULE, IN_BODY, IS_CAPITAL, FORMAT_TYPE, RESPONSE_RESULT)
values (135035, 65010, 1, 'string', 'transParam', '', 'response', 'NO_NEED', '', 'response', 'true', 'unchanged', '', 'fail');

insert into INTERFACE_PACKETS_DEFINITION (ID, IS_CONF, MERCHANT_ID, INTERFACE_TYPE, IN_OR_OUT, ENCODING, CONNECTION_TYPE, REQUEST_URL, ENTITY_NAME, STATUS)
values (65004, 1, null, 'reciever_TBorder', 'in', 'gbk', null, null, null, 'open');

insert into INTERFACE_PACKETS_DEFINITION (ID, IS_CONF, MERCHANT_ID, INTERFACE_TYPE, IN_OR_OUT, ENCODING, CONNECTION_TYPE, REQUEST_URL, ENTITY_NAME, STATUS)
values (65001, 1, null, 'reciever_order', 'in', 'gbk', null, null, null, 'open');

insert into INTERFACE_PACKETS_DEFINITION (ID, IS_CONF, MERCHANT_ID, INTERFACE_TYPE, IN_OR_OUT, ENCODING, CONNECTION_TYPE, REQUEST_URL, ENTITY_NAME, STATUS)
values (65007, 1, null, 'agent_query_order', 'in', 'gbk', null, null, null, 'open');

insert into INTERFACE_PACKETS_DEFINITION (ID, IS_CONF, MERCHANT_ID, INTERFACE_TYPE, IN_OR_OUT, ENCODING, CONNECTION_TYPE, REQUEST_URL, ENTITY_NAME, STATUS)
values (65010, 1, null, 'agent_query_TBorder', 'in', 'gbk', null, null, null, 'open');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (3312, null, 'reciever_order', 'response', 'text/xml');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (4053, null, 'agent_query_order', 'response', 'text/xml');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (4052, null, 'agent_query_TBorder', 'response', 'text/xml');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (3308, null, 'agent_query_order', 'request', 'text/plain');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (3311, null, 'agent_query_TBorder', 'request', 'text/plain');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (3310, null, 'reciever_TBorder', 'request', 'text/plain');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (3309, null, 'reciever_TBorder', 'response', 'text/xml');

insert into interface_packet_type_conf (ID, MERCHANT_ID, INTERFACE_TYPE, CONNECTION_MODULE, PACKET_TYPE)
values (3305, null, 'reciever_order', 'request', 'text/plain');

commit;
--城市遗漏修复 2015/02/02
insert into city (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0955', '中卫', 0, 'NX_');
insert into city (CITY_ID, CITY_NAME, STATUS, PROVINCE_ID)
values ('0910', '淳化', 0, 'SX1');
commit;

--增加账户未开启错误码 2015/02/02
insert into response_code_translation (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (49, 'reciever_order', '600', 'false', '600', '商户账户未开启');

insert into response_code_translation (ID, INTERFACE_TYPE, ERROR_CODE, COOP_ORDER_STATUS, FAILED_CODE, MSG)
values (50, 'reciever_Tborder', '600', 'ORDER_FAILED', '0503', '充值失败');
commit;