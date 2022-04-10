package com.hdp.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PredictionVO {

	private String emailId;

	private String age;

	private String gender;

	private String cig;

	private String chol;

	private String dia;

	private String sys;

	private String diab;

	private String glu;

	private String heartrate;

	private String result;
}
