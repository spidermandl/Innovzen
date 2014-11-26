package com.innovzen.bluetooth.check;

import com.innovzen.fragments.base.FragBase;

public interface CheckInterface {
	/**
	 * 设置ui fragment
	 * @param frag
	 */
	public void setUiHandler(FragBase frag);
	/**
	 * 初始化状态变量
	 */
	public void initlize();
	/**
	 * 开启检测有效命令
	 * @param trigger
	 */
	public void setTrigger(boolean trigger);
	/**
	 * 判断机器是否复位
	 * isLog 是否显示提示
	 * @return
	 */
	public boolean isReseted(boolean isLog) ;
    /**
     * 获取机器关闭开始按钮状态
     * @param isLog
     * @return 没有运行过
     *         正在运行
     *         运行结束
     */
	public int startOrStop(boolean isLog);
	
	/**
	 * 停止所有线程
	 */
	public void stopThreads();
}
