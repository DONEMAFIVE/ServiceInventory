package parsing;

public class Inventory {
    private String mol;
    private String name;
    private String inumber;
    private String cost;
    private String count;

    @Override
    public String toString() {
        return  "{mol='" + mol + '\'' +
                ", name='" + name + '\'' +
                ", number='" + inumber + '\'' +
                ", cost='" + cost + '\'' +
                ", count=" + count +
                '}';
    }
    public Inventory(String mol, String name,  String inumber, String cost, String count) {
        this.mol = mol;
        this.name = name;
        this.inumber = inumber;
        this.cost = cost;
        this.count = count;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMol() {
        return mol;
    }
    public void setMol(String mol) {
        this.mol = mol;
    }
    public String getInumber() {
        return inumber;
    }
    public void setInumber(String inumber) {
        this.inumber = inumber;
    }
    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
}
