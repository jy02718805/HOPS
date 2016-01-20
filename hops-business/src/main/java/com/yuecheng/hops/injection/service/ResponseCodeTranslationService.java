package com.yuecheng.hops.injection.service;

import java.util.Map;

public interface ResponseCodeTranslationService {
	public Map<String,Object> translationMapToResponse(String interfaceType,Map<String,Object> response_fields);
}
