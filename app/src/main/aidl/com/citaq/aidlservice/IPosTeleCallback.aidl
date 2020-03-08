/**
* Version: 1.0
*/

package com.citaq.aidlservice;

interface IPosTeleCallback {
	/**
	* 来电的月日时分及电话号码, 如主叫方不同意显示的话,号码会显示成P
	*/
	void onCalling(int month, int day, int hour, int minute, String phone );
}