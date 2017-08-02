package com.mall.b2bp.enums;

public enum ImageType {
	//FRONT(1,"front"), SIDE(2, "side"), BACK(3, "back"), ALT1(4,"alt1"), ALT2(5,"alt2"), NUTRI(6,"nutri");
	FRONT(1,"front"), SIDE(2, "side"), BACK(3, "back"), ALT1(4,"nutri"), ALT2(5,"alt1"), NUTRI(6,"alt2");
	
	private String name;
    private int index;
    
    private ImageType(int index, String name) {
        this.index = index;
        this.name = name;
    }
    
    public static String getName(int index) {
        for (ImageType type : ImageType.values()) {
            if (type.getIndex() == index) {
                return type.name;
            }
        }
        return null;
    }
    
    public static int getIndex(String name) {
        for (ImageType type : ImageType.values()) {
            if (type.getName().equals(name)) {
                return type.index;
            }
        }
        return 0;
    }
    

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
