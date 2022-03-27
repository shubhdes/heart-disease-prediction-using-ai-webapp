package com.hdp.vo;

import java.util.Map;

import com.hdp.utils.ProcessStatus;

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
public class ProcessVO {

	private ProcessStatus processStatus;

	private Map<String, Object> processAttributes;
}
