package com.example.ndk_patch.utils;

public class BsPatch {

	/**
	 * 合并
	 * 
	 * @param oldfile
	 * @param newfile
	 * @param patchfile
	 */
	public native static void patch(String oldfile, String newfile, String patchfile);

	static{
		System.loadLibrary("bspatch");
	}
	
}
