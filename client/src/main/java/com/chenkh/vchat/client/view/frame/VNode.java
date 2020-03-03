package com.chenkh.vchat.client.view.frame;

import java.util.Collections;

import javax.swing.tree.DefaultMutableTreeNode;

import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.User;

public class VNode extends DefaultMutableTreeNode implements Comparable<VNode> {

	public VNode(Object user) {
		super(user);

	}

	public void reSort() {
		if (this.userObject.getClass() == User.class) {
			if (!this.children.isEmpty()) {
				Collections.sort(children);
				for (Object obj : this.children) {
					VNode node = (VNode) obj;
					node.reSort();
				}
			}

		} else {
			if (!this.children.isEmpty()) {
				Collections.sort(this.children);
			}
		}
	}

	public boolean removeChildByUserObject(Object obj) {
		if (obj == null)
			return false;

		for (Object vobj : this.children) {
			if (vobj.getClass() == VNode.class) {
				VNode node = (VNode) vobj;
				if (node.getUserObject().equals(obj)) {
					this.remove(node);
					return true;
				}

			}

		}

		return false;

	}

	public VNode getChildByChildUserObject(Object userObj) {

		if (userObj == null) {
			return null;
		}

		for (Object vobj : this.children) {
			if (vobj.getClass() == VNode.class) {
				VNode node = (VNode) vobj;
				if (node.getUserObject().equals(userObj)) {
					return node;
				}

			}

		}
		
		return null;
	}

	public int getIndexByChidren(Object obj) {
		if (obj == null) {
			return -1;
		}

		if (this.children.contains(obj)) {
			return children.indexOf(obj);
		}
		return -1;

	}

	public int getIndexByUserObject(Object obj) {
		if (obj == null) {
			return -1;
		}

		for (Object vobj : this.children) {
			if (vobj.getClass() == VNode.class) {
				VNode node = (VNode) vobj;
				if (node.getUserObject().equals(obj)) {
					return this.children.indexOf(vobj);
				}

			}

		}

		return -1;

	}

	@Override
	public int hashCode() {

		return this.getUserObject().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj.getClass() == this.getClass()) {
			VNode node = (VNode) obj;
			return node.getUserObject().equals(this.getUserObject());
		}

		return false;

	}

	@Override
	public int compareTo(VNode o) {

		Object userObj1 = this.userObject;

		Object userObj2 = o.getUserObject();

		if (userObj1.getClass() == Group.class
				&& userObj2.getClass() == Group.class) {
			Group group1 = (Group) userObj1;
			Group group2 = (Group) userObj2;
			return group1.compareTo(group2);

		} else if (userObj1.getClass() == Friend.class
				&& userObj2.getClass() == Friend.class) {
			Friend friend1 = (Friend) userObj1;
			Friend friend2 = (Friend) userObj2;

			return friend1.compareTo(friend2);
		}

		return 0;
	}

}
