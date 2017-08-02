package com.mall.b2bp.enums;

public enum LovType {
	COLOR_GROUP("524", "Color Group"),
	// VARIANT_COLOR("501", "Variant Color"), VARIANT_SIZE("502",
	// "Variant Size"),
			STANDARD_UOM("401", "Standard UOM"),
			UOM_CBM("402", "UOM CBM"), 
			UOM_WEIGHT("403", "UOM Weight"),
			ORIGIN_COUNTRY("404", "Origin Country"),
			ESTORE_CATEGORY("789", "eStore category"),
			MIN_ORDER_QTY("569","Minimum order quantity"),
			DAILY_INVENTORY("784", "Daily inventory"),
			MIN_DELIVER_DATE("785", "Minimum deliver date"),
			MAX_DELIVER_DATE("786","Maximum deliver date"),
			ITEM_NUM_TYPE("405", "Item Number Type"),
			PACKAGE("776", "Package"), 
			PACKED_IN("82", "Packed in"),
			ONLINE_ITEM_DELIVERY_TYPE("782", "Online Item Delivery Type"),
			ONLINE_ITEM_CASH_FLOW("801", "\u91d1\u6d41"),
			ONLINE_ITEM_MATERIAL_FLOW("802", "\u7269\u6d41");

	private String lovId;
	private String lovDesc;

	private LovType(String lovId, String lovDesc) {
		this.lovId = lovId;
		this.lovDesc = lovDesc;
	}

	public static LovType getType(String lovId) {
		for (LovType type : LovType.values()) {
			if (type.getLovId().equals(lovId)) {
				return type;
			}
		}
		return null;
	}

	public String getLovId() {
		return lovId;
	}

	public String getLovDesc() {
		return lovDesc;
	}

}
