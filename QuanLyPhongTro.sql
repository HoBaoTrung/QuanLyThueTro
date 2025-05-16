CREATE database QuanLyPhongTro;
use QuanLyPhongTro;

CREATE TABLE payment_method (
    payment_method_id INT AUTO_INCREMENT PRIMARY KEY,
    method_name VARCHAR(20) NOT NULL
);

INSERT INTO payment_method (method_name) VALUES
('theo tháng'),
('theo quý'),
('theo năm');

CREATE TABLE rental_room (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    tenant_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    start_rent_date DATE NOT NULL,
    payment_method_id INT NOT NULL,
    note VARCHAR(200),
    FOREIGN KEY (payment_method_id) REFERENCES payment_method(payment_method_id)
);

INSERT INTO rental_room (tenant_name, phone_number, start_rent_date, payment_method_id, note) VALUES
('Nguyễn Văn A', '0912345678', '2025-05-01', 1, 'Thanh toán đúng hạn'),
('Trần Thị B', '0987654321', '2025-05-10', 2, ''),
('Lê Văn C', '0909123456', '2025-06-01', 3, 'Cần gia hạn hợp đồng'),
('Nguyễn Tố D', '0912349436', '2025-05-01', 1, 'Điều hòa hỏng'),
('Trần Quốc E', '0980943321', '2025-05-10', 2, ''),
('Ngô Văn F', '0351923456', '2025-06-01', 3, 'Sắp hết hợp đồng');
