import java.io.*;
import datatypes.*;

/* Importing datatypes inclues following:
	enumerator Currency
	enumerator ReservationStatus
	enumerator UserRole
	enumerator CommodityState
	enumerator CommodityPriceCounter
	class CommodityPriceCounter
	
	sources are in src/DataTypes
*/

public class DataTypesExample {
	public static void main(String []args) {
		System.out.printf("UserRole MANAGER: %s\n", UserRole.MANAGER.toString());
		CommodityPrice c = new CommodityPrice(10.f, Currency.CZK, CommodityPriceCounter.NIGHT);
		System.out.printf("Commodity value: %f\n", c.value);
		System.out.printf("Commodity currency: %s\n", c.currency.toString());
		System.out.printf("Commodity pcounter: %s\n", c.valuePer.toString());
	}
}