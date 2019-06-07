import java.util.HashMap;
import java.util.LinkedHashMap;

public class testClass {
	static void message(String s) {
		System.out.println(s);
	}
	
	public static void main(String arg[]) {
		message("******************************");
		message("Test Start");
		message("******************************\n");
		
		message("No Warehouse");
		message("Testing InventoryAllocator()");
		InventoryAllocator ia1 = new InventoryAllocator();
		message("Testing arrangeShipment() with no warehosue");
		HashMap<String, Integer> order1 = new HashMap<String, Integer>();
		order1.put("apple", 3);
		assert(ia1.arrangeShipment(order1).size() == 0);
		message("Testing item whose name is empty string");
		order1.put("", 1);
		assert(ia1.arrangeShipment(order1).size() == 0);
		message("Testing item whose amount is 0");
		order1.put("apple", 0);
		assert(ia1.arrangeShipment(order1).size() == 0);
		message("Test 1 passed");
		
		message("\nSingle Warehouse");
		message("Testing InventoryAllocator(LinkedHashMap) and print()");
		LinkedHashMap<String, HashMap<String, Integer>> warehouses = new LinkedHashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> inventory = new HashMap<String, Integer>();
		inventory.put("apple", 5);
		inventory.put("banana", 10);
		inventory.put("orange", 10);
		warehouses.put("owd", inventory);
		InventoryAllocator ia2 = new InventoryAllocator(warehouses);
		message("owd has 5 apples, 10 bananas and 10 oranges");
		ia2.print();
		message("Testing arrangeShipment() with one warehosue");
		HashMap<String, Integer> order2 = new HashMap<String, Integer>();
		order1.put("apple", 3);
		assert(ia2.arrangeShipment(order1).get("owd").get("apple") == 3);
		message("Testing order that asks for more items than stock");
		order1.put("banana", 100);
		assert(ia2.arrangeShipment(order1).get("owd").get("banana") == 10);
		message("Testing item that does not exist in inventory");
		order2.put("chocolate", 1);
		assert(ia2.arrangeShipment(order1).get("owd").size() == 2);
		message("Testing item whose amount is 0");
		order1.put("apple", 0);
		assert(ia2.arrangeShipment(order1).size() == 1);
		assert(ia2.arrangeShipment(order1).get("owd").get("banana") == 10);
		message("Test 2 passed");
		
		message("\nMultiple Warehouses");
		HashMap<String, Integer> inventory2 = new HashMap<String, Integer>();
		HashMap<String, Integer> inventory3 = new HashMap<String, Integer>();
		HashMap<String, Integer> inventory4 = new HashMap<String, Integer>();
		inventory2.put("apple", 10);
		inventory2.put("laptop", 5);
		inventory2.put("pie", 5);
		inventory3.put("pasta", 10);
		inventory3.put("banana", 10);
		inventory3.put("tomato", 10);
		inventory4.put("chocolate", 100);
		warehouses.put("dm", inventory2);
		warehouses.put("uci", inventory3);
		warehouses.put("deliverr", inventory4);
		InventoryAllocator ia3 = new InventoryAllocator(warehouses);
		message("Testing warehouses order: owd -> dm -> uci -> deliverr");
		ia3.print();
		message("Testing order that can be completed by the first warehouse only");
		HashMap<String, Integer> order3 = new HashMap<String, Integer>();
		order3.put("apple", 4);
		order3.put("banana", 10);
		assert(ia3.arrangeShipment(order3).size() == 1);
		assert(ia3.arrangeShipment(order3).get("omd").size() == 2);
		message("Testing order that requires multiple warehouses to split shared items");
		order3.put("apple", 12);
		order3.put("banana", 20);
		assert(ia3.arrangeShipment(order3).size() == 3);
		assert(ia3.arrangeShipment(order3).get("dm").get("apple").equals(7));
		assert(ia3.arrangeShipment(order3).get("uci").get("banana").equals(10));
		message("Testing order that can only be completed by the last warehouse");
		order3.clear();
		order3.put("chocolate", 50);
		assert(ia3.arrangeShipment(order3).size() == 1);
		assert(ia3.arrangeShipment(order3).get("deliverr").size() == 1);
		message("Test 3 passed");
		

		message("\n******************************");
		message("All Tests Passed");
		message("******************************");
		
	}
}
