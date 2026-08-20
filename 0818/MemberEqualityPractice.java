import java.util.Objects;

class LibraryMember {
    private String memberId;
    private String name;
    private String email;

    public LibraryMember(String memberId, String name, String email) {
        if (memberId == null || memberId.trim().isEmpty()) {
            this.memberId = "Unknown";
        } else {
            this.memberId = memberId;
        }

        if (name == null || name.trim().isEmpty()) {
            this.name = "Unknown";
        } else {
            this.name = name;
        }

        if (email == null || email.trim().isEmpty()) {
            this.email = "Unknown";
        } else {
            this.email = email;
        }
    }

    public String getMemberId() {
        return this.memberId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "會員編號: " + this.memberId + ", 姓名: " + this.name + ", 電子郵件: " + this.email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LibraryMember other = (LibraryMember) obj;
        return Objects.equals(this.memberId, other.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.memberId);
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember member1 = new LibraryMember("M001", "金泰亨", "thv@example.com");
        LibraryMember member2 = new LibraryMember("M001", "金泰亨", "thv_bts@example.com");
        LibraryMember member3 = null;

        System.out.println("=== 物件資訊 ===");
        System.out.println("Member 1: " + member1);
        System.out.println("Member 2: " + member2);

        System.out.println("\n=== 比較結果 ===");
        System.out.println("member1 == member2: " + (member1 == member2));
        System.out.println("member1.equals(member2): " + member1.equals(member2));
        System.out.println("member1.equals(null): " + member1.equals(member3));
    }
}