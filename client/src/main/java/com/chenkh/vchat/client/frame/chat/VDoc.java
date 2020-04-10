package com.chenkh.vchat.client.frame.chat;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

import com.chenkh.vchat.client.UserMgr;
import com.chenkh.vchat.client.enu.AttSetType;

/**
 * 继承DefaultStyledDocument,
 */
public class VDoc extends DefaultStyledDocument {
	private boolean lastIsFriend = false;
	private String userName;
	private String friendName;
	private boolean first = true;
	private DefaultCaret caret = new DefaultCaret();
	
	
	public VDoc(){
		super();
		//caret.
	//	JTextPane pane=  new JTextPane();

		
		
	}
	
	
	
	public Caret getCaret(){
		return caret;
	}
	
	

	public VDoc(String friendName) {
		this.userName = UserMgr.getInstance().getUserNmae();
		this.friendName = friendName;

	}

	private void insertUserName() {
		SimpleAttributeSet set = UserMgr.getInstance().getAttributeSet(
				AttSetType.userName);
		this.insert(set, userName);

	}

	private void insertFriendName() {

		SimpleAttributeSet set = UserMgr.getInstance().getAttributeSet(
				AttSetType.friendName);
		this.insert(set, friendName);

	}

	public void addSelfMsg(String msg) {
		SimpleAttributeSet set = UserMgr.getInstance().getAttributeSet(
				AttSetType.userMsg);
		if(this.first){
			this.insertUserName();
			first = false;
		}
		if (this.lastIsFriend) {
			
			this.insertUserName();
		}
		
		
		this.insert(set, "●"+msg+"\n");

		this.lastIsFriend = false;

		

	}

	public void addFriendMsg(String msg) {
		
		SimpleAttributeSet set = UserMgr.getInstance().getAttributeSet(
				AttSetType.friendMsg);
		if (!this.lastIsFriend) {
			this.insertFriendName();
		}

		this.insert(set,"●"+ msg+"\n");

		this.lastIsFriend = true;
	}

	private void insert(SimpleAttributeSet set, String str) {

		int star = this.getLength();
		str += "\n";
	
		int end = star + str.length();
		

		try {
			this.insertString(star, str, set);
		} catch (BadLocationException e) {

			e.printStackTrace();
		}

		this.setParagraphAttributes(star, end - star, set, true);
		
		
		

	}

}
