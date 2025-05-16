<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Danh sách phòng thuê trọ</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<%--  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>--%>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>
<body>
<div class="container">
  <h2>Danh sách phòng thuê trọ</h2>

  <form method="get" action="${pageContext.request.contextPath}/rental">
    <input type="text" name="keyword" value="${keyword}" placeholder="Tìm kiếm theo mã, tên, số điện thoại" />
    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
  </form>

  <button class="btn btn-success mb-3 float-end" onclick="toggleForm()">Tạo mới</button>

  <div id="createForm" class="card mt-3" style="display: none;">
    <div class="card-body">
      <form method="post" action="/rental?action=create">
        <div class="mb-2">
          <label>Tên người thuê</label>
          <input type="text" name="tenantName" class="form-control" required />
        </div>
        <div class="mb-2">
          <label>Số điện thoại</label>
          <input type="text" name="phoneNumber" class="form-control" required maxlength="10" />
        </div>
        <div class="mb-2">
          <label>Ngày bắt đầu thuê</label>
          <input type="date" name="startRentDate" class="form-control" required min="${currentDate}" />
        </div>
        <div class="mb-2">
          <label>Hình thức thanh toán</label>
          <select name="paymentMethodId" class="form-select" required>
            <c:forEach var="method" items="${paymentMethods}">
              <option value="${method.id}">${method.name}</option>
            </c:forEach>
          </select>
        </div>
        <div class="mb-2">
          <label>Ghi chú</label>
          <textarea name="note" class="form-control" maxlength="200"></textarea>
        </div>

        <c:if test="${not empty errors}">
          <div class="alert alert-danger">
            <ul>
              <c:forEach var="err" items="${errors}">
                <li>${err}</li>
              </c:forEach>
            </ul>
          </div>
        </c:if>

        <div class="text-end">
          <button type="submit" class="btn btn-primary">Tạo mới</button>
          <button type="button" class="btn btn-secondary" onclick="toggleForm()">Hủy</button>
        </div>
      </form>
    </div>
  </div>

  <form method="post" action="/rental?action=delete" id="deleteForm">
    <table class="table table-bordered">
      <thead>
      <tr>
        <th>Mã phòng</th>
        <th>Tên người thuê</th>
        <th>Số điện thoại</th>
        <th>Ngày bắt đầu thuê</th>
        <th>Hình thức thanh toán</th>
        <th>Ghi chú</th>
        <th><input type="checkbox" id="selectAll" /></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="room" items="${rentalRooms}">
        <tr>
          <td>${room.roomId}</td>
          <td>${room.tenantName}</td>
          <td>${room.phoneNumber}</td>
          <td>${room.startRentDate}</td>
          <td>${room.paymentMethodName}</td>
          <td>${room.note}</td>
          <td><input type="checkbox" name="roomIds" value="${room.roomId}" /></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

    <button type="button" class="btn btn-danger float-end" id="btnDelete">Xóa</button>
  </form>

</div>

<script>

  function toggleForm() {
    const form = document.getElementById("createForm");
    form.style.display = (form.style.display === "none") ? "block" : "none";
  }

  // Chọn tất cả checkbox
  document.getElementById('selectAll').onclick = function () {
    let checkboxes = document.querySelectorAll('input[name="roomIds"]');
    for (let cb of checkboxes) {
      cb.checked = this.checked;
    }
  };

  document.getElementById('btnDelete').addEventListener('click', function () {
    let selected = [];
    let checkboxes = document.querySelectorAll('input[name="roomIds"]:checked');
    checkboxes.forEach(cb => {
      selected.push(cb.value);
    });

    if (selected.length === 0) {
      Swal.fire({
        icon: 'warning',
        title: 'Chưa chọn phòng để xóa',
        confirmButtonText: 'OK'
      });
      return;
    }

    Swal.fire({
      title: 'Bạn có chắc chắn muốn xóa?',
      html: 'Bạn có muốn xóa thông tin thuê trọ <b>' + selected.join(', ') + '</b> không?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Có',
      cancelButtonText: 'Không',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        document.getElementById('deleteForm').submit();
      }
    });
  });
</script>

</body>
</html>
