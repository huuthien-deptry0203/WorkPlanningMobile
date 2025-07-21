package com.example.workplanning.data

import com.example.workplanning.Task


object ListTask {
    val listTask = mutableListOf(
        Task(
            id = "1",
            title = "Thiết kế giao diện",
            date = "2025-07-01 14:00",
            description = "Thiết kế UI cho trang chủ",
            isDone = true
        ),
        Task(
            id = "2",
            title = "Họp nhóm tuần",
            date = "2025-07-15 10:00",
            description = "Báo cáo tiến độ Sprint 2",
            isDone = false
        ),
        Task(
            id = "3",
            title = "Phân tích yêu cầu khách hàng",
            date = "2025-06-28 09:00",
            description = "Gặp khách để phân tích nhu cầu",
            isDone = false
        ),
        Task(
            id = "4",
            title = "Triển khai Backend",
            date = "2025-07-18 18:00",
            description = "Tạo các API cho chức năng đăng nhập",
            isDone = true
        ),
        Task(
            id = "5",
            title = "Kiểm thử hệ thống",
            date = "2025-07-22 16:30",
            description = "Viết test case và chạy thử nghiệm",
            isDone = false
        ),
        Task(
            id = "6",
            title = "Tối ưu hiệu năng",
            date = "2025-07-12 13:00",
            description = "Refactor code và giảm thời gian phản hồi",
            isDone = true
        ),
        Task(
            id = "7",
            title = "Đào tạo người dùng",
            date = "2025-07-20 08:30",
            description = "Hướng dẫn sử dụng phần mềm cho khách hàng",
            isDone = false
        ),
        Task(
            id = "8",
            title = "Gửi báo giá",
            date = "2025-06-30 15:45",
            description = "Soạn và gửi bảng giá cho đối tác",
            isDone = false
        ),
        Task(
            id = "9",
            title = "Cập nhật tài liệu kỹ thuật",
            date = "2025-07-25 11:00",
            description = "Hoàn thiện tài liệu thiết kế hệ thống",
            isDone = false
        ),
        Task(
            id = "10",
            title = "Dọn dẹp database",
            date = "2025-07-05 17:00",
            description = "Xóa dữ liệu test, tối ưu bảng",
            isDone = true
        )
    )
}
