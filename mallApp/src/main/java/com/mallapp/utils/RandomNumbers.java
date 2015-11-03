package com.mallapp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomNumbers {
	
	
	public static String createAccessCode(){
		List<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++){
			numbers.add(i);
		}

		Collections.shuffle(numbers);
		String result = "";
		for(int i = 0; i < 4; i++){
			result += numbers.get(i).toString();
		}
		System.out.println(result);
		return result;
	}
	
	
	
}
