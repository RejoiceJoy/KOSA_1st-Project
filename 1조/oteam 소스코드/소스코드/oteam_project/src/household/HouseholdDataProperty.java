package household;

import javafx.beans.property.*;
import java.sql.Date;



/*코드 작성자 : 김준영*/
//javaFX의 tableView의 Property(속성) 정의
public class HouseholdDataProperty {     
	//tableView의 열 선언
	private IntegerProperty Household_Code;     
	private StringProperty Household_Type;
	private StringProperty Household_Category;
	private SimpleObjectProperty<Date> Household_Date;
	private IntegerProperty Household_Money;
	private StringProperty Household_Memo;
	private IntegerProperty Member_Code;
	
	
	
	//tableView의 열 객체 생성
	public HouseholdDataProperty() {
		this.Household_Code = new SimpleIntegerProperty();
		this.Household_Type = new SimpleStringProperty();
		this.Household_Category = new SimpleStringProperty();
		this.Household_Date = new SimpleObjectProperty<>();
		this.Household_Money = new SimpleIntegerProperty();
		this.Household_Memo = new SimpleStringProperty();
		this.Member_Code = new SimpleIntegerProperty();
	}
	
	
	//tableView의 getter, setter
	public final IntegerProperty Household_CodeProperty() {
		return this.Household_Code;
	}
	public final int getHousehold_Code() {
		return this.Household_CodeProperty().get();
	}
	public final void setHousehold_Code(final int Household_Code) {
		this.Household_CodeProperty().set(Household_Code);
	}
	


	public final StringProperty Household_TypeProperty() {
		return this.Household_Type;
	}
	public final String getHousehold_Type() {
		return this.Household_TypeProperty().get();
	}
	public final void setHousehold_Type(final String Household_Type) {
		this.Household_TypeProperty().set(Household_Type);
	}
	


	public final StringProperty Household_CategoryProperty() {
		return this.Household_Category;
	}
	public final String getHousehold_Category() {
		return this.Household_CategoryProperty().get();
	}
	public final void setHousehold_Category(final String Household_Category) {
		this.Household_CategoryProperty().set(Household_Category);
	}
	


	public final SimpleObjectProperty<Date> Household_DateProperty() {
		return this.Household_Date;
	}
	public final Date getHousehold_Date() {
		return this.Household_DateProperty().get();
	}
	public final void setHousehold_Date(final Date Household_Date) {
		this.Household_DateProperty().set(Household_Date);
	}
	


	public final IntegerProperty Household_MoneyProperty() {
		return this.Household_Money;
	}
	public final int getHousehold_Money() {
		return this.Household_MoneyProperty().get();
	}
	public final void setHousehold_Money(final int Household_Money) {
		this.Household_MoneyProperty().set(Household_Money);
	}
	


	public final StringProperty Household_MemoProperty() {
		return this.Household_Memo;
	}
	public final String getHousehold_Memo() {
		return this.Household_MemoProperty().get();
	}
	public final void setHousehold_Memo(final String Household_Memo) {
		this.Household_MemoProperty().set(Household_Memo);
	}
	


	public final IntegerProperty Member_CodeProperty() {
		return this.Member_Code;
	}
	public final int getMember_Code() {
		return this.Member_CodeProperty().get();
	}
	public final void setMember_Code(final int Member_Code) {
		this.Member_CodeProperty().set(Member_Code);
	}
	

	
}





