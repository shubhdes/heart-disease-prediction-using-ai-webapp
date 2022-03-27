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
public class CustomerVO {

	private String fname;

	private String lname;

	private String emailId;

	private String mobile;

	private String secretQuestion;

	private String answer;
}
