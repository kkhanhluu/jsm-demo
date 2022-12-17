package com.github.kkhanhluu.jmsdemo.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {
	private static final long serialVersionUID = 4420932453294507681L;

	private UUID id;
	private String message;
}
