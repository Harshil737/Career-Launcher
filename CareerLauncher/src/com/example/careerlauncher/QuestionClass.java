package com.example.careerlauncher;

public class QuestionClass {

	String question, op1, op2, op3, op4, is_correct;
	int que_id, fk_quiz_id;

	public QuestionClass(int que_id, int kf_que_id, String qestion, String op1,
			String op2, String op3, String op4, String is_correct) {
		super();
		this.que_id = que_id;
		this.question = qestion;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.op4 = op4;
		this.is_correct = is_correct;
		this.fk_quiz_id = kf_que_id;
	}

	public int getQue_id() {
		return que_id;
	}

	public void setQue_id(int que_id) {
		this.que_id = que_id;
	}

	public String getQestion() {
		return question;
	}

	public void setQestion(String qestion) {
		this.question = qestion;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}

	public String getOp3() {
		return op3;
	}

	public void setOp3(String op3) {
		this.op3 = op3;
	}

	public String getOp4() {
		return op4;
	}

	public void setOp4(String op4) {
		this.op4 = op4;
	}

	public String getIs_correct() {
		return is_correct;
	}

	public void setIs_correct(String is_correct) {
		this.is_correct = is_correct;
	}

	public int getFk_que_id() {
		return fk_quiz_id;
	}

	public void setFk_que_id(int fk_que_id) {
		this.fk_quiz_id = fk_que_id;
	}

}
