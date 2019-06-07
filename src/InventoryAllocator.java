import java.util.HashMap;
import java.util.LinkedHashMap;




/**
 * InventoryAllocator is a class for finding optimal solution of
 * order shipments across a set of warehouses
 * @author Wuchi Wang
 *
 */
public class InventoryAllocator {
	private LinkedHashMap<String, HashMap<String, Integer>> warehouse;
	
	/**
	 * Default Constructor for InventoryAllocator
	 * Initialize the warehouses to be empty
	 */
	InventoryAllocator(){
		warehouse = new LinkedHashMap<String, HashMap<String, Integer>>();
	}
	
	/**
	 * Constructor for Inventory with a set of warehouses
	 * @param ware - LinkedHashMap of String (name of warehouse) and 
	 *               HashMap(inventory) <String(name of item), Integer(amount in the inventory)>
	 */
	InventoryAllocator(LinkedHashMap<String, HashMap<String, Integer>> wareHouse){
		warehouse = wareHouse;
	}
	
	/**
	 * Insert a new warehouse to the allocator
	 * If a warehouse with the same name already exist, increment the number of items in the warehouse
	 * @param name		- Name of the warehouse
	 * @param inventory	- Items in the warehouse
	 */
	void insertWarehouse(String name, HashMap<String, Integer> inventory) {
		if (warehouse.containsKey(name)) {
			for (String item : inventory.keySet()) {
				if (warehouse.get(name).containsKey(item)) {
					warehouse.get(name).put(item, warehouse.get(name).get(item)+inventory.get(item));
				}
				else {
					warehouse.get(name).put(item, inventory.get(item));
				}
			}
		}
		else {
			warehouse.put(name, inventory);
		}
	}
	
	/**
	 * Delete a whole warehouse and items inside it from the Allocator
	 * @param name - Name of the warehouse to be deleted
	 */
	void deleteWarehouse(String name) {
		if (warehouse.containsKey(name))
			warehouse.remove(name);
	}
	
	/**
	 * Delete corresponding items from the Allocator by name of warehouses and items and amount
	 * Warehouses that are empty after deletion are still kept in the Allocator in order to 
	 * keep its index for future usage (so the cost order stays the same)
	 * @param items - List of items to be deleted (with name of the warehouses they belong to)
	 */
	void deleteWarehouse(HashMap<String, HashMap<String, Integer>> items) {
		for (String location : items.keySet()) {
			for (String item : items.get(location).keySet()) {
				if (warehouse.get(location).get(item).equals(items.get(location).get(item))) {
					warehouse.get(location).remove(item);
				}
				else {
					warehouse.get(location).put(item, 
							warehouse.get(location).get(item) - items.get(location).get(item));
				}
			}
		}
	}
	
	/**
	 * Find optimal solution of a shipment
	 * @param order - List of items in the order
	 * @return		- How many of each item to be taken from each warehouse
	 */
	HashMap<String, HashMap<String, Integer>> arrangeShipment(HashMap<String, Integer> order){
		HashMap<String, HashMap<String, Integer>> shipment = new HashMap<String, HashMap<String, Integer>>();
		for (String location : warehouse.keySet()) {
			boolean tookItem = false;
			HashMap<String, Integer> itemsTaken = new HashMap<String, Integer>();
			for (String item : order.keySet()) {
				if (order.get(item).equals(0)) {
					order.remove(item);
				}
				else if (warehouse.get(location).containsKey(item)) {
					tookItem = true;
					if (warehouse.get(location).get(item) >= order.get(item)) {
						itemsTaken.put(item, order.get(item));
						order.remove(item);
					} 
					else {
						itemsTaken.put(item, warehouse.get(location).get(item));
						order.put(item, order.get(item) - warehouse.get(location).get(item));
					}
				}
			}
			if (tookItem)
				shipment.put(location, itemsTaken);
			if (order.size() == 0)
				break;
		}
		return shipment;
	}
	
	/**
	 * Print each warehouse and its inventory in order
	 */
	void print() {
		for (String location : warehouse.keySet()) {
			System.out.println(location + ": ");
			for (String item : warehouse.get(location).keySet()) {
				System.out.println("    " + item + ": " + warehouse.get(location).get(item));
			}
		}
	}
}
