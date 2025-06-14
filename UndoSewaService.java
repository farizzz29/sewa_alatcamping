package stack;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UndoSewaService {
    private StackSewa stack;

    public UndoSewaService() {
        stack = new StackSewa();
    }

    public void pushSewa(int idPenyewaan) {
        stack.push(idPenyewaan);
    }

    public void undoSewaTerakhir() {
        if (stack.isEmpty()) {
            System.out.println("Tidak ada penyewaan yang bisa dibatalkan.");
            return;
        }

        int idUndo = stack.pop();

        try (Connection conn = DBConnection.getConnection()) {
            // Cek status sewa
            String query = "SELECT status FROM penyewaan WHERE id_penyewaan = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idUndo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");

                if ("Belum_Disetujui".equalsIgnoreCase(status)) {
                    // Hapus detail penyewaan (anak)
                    PreparedStatement stmtDetail = conn.prepareStatement(
                        "DELETE FROM detail_penyewaan WHERE id_penyewaan = ?"
                    );
                    stmtDetail.setInt(1, idUndo);
                    stmtDetail.executeUpdate();

                    // Hapus penyewaan (induk)
                    PreparedStatement stmtPenyewaan = conn.prepareStatement(
                        "DELETE FROM penyewaan WHERE id_penyewaan = ?"
                    );
                    stmtPenyewaan.setInt(1, idUndo);
                    stmtPenyewaan.executeUpdate();

                    System.out.println("Penyewaan terakhir berhasil dibatalkan.");
                } else {
                    System.out.println("Penyewaan sudah disetujui, tidak bisa dibatalkan.");
                }
            } else {
                System.out.println("Data penyewaan tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat membatalkan penyewaan: " + e.getMessage());
        }
    }
}
