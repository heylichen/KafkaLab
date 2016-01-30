package com.heylichen.amq.publish;

import com.heylichen.amq.vo.Spittle;

public interface AlertService {

	void sendSpittleAlert(Spittle spittle);

}