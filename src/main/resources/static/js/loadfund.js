const ongoingContainer = document.getElementById("ongoing-container");
const completedContainer = document.getElementById("completed-container");
const ongoingLoadMore = document.getElementById("ongoing-load-more");
const completedLoadMore = document.getElementById("completed-load-more");

// Số lượng mỗi lần tải
const CARD_INCREASE = 3;

const funds = /*[[${funds}]]*/ []; // Lấy danh sách quỹ từ server thông qua Thymeleaf

const loadFunds = (container, loadMoreButton, filterCallback) => {
    const currentPage = parseInt(container.getAttribute("data-current-page"));
    const startIndex = (currentPage - 1) * CARD_INCREASE;
    const endIndex = Math.min(startIndex + CARD_INCREASE, funds.length);

    // Lọc dữ liệu
    const filteredFunds = funds.filter(filterCallback).slice(startIndex, endIndex);

    // Thêm thẻ card
    filteredFunds.forEach((fund) => {
        const card = document.createElement("div");
        card.className = "col-md-4";
        card.innerHTML = `
      <a class="text-decoration-none" style="color: black;">
        <div class="donation-item d-flex flex-column overflow-hidden rounded border border-light bg-white text-dark shadow-sm transition">
          <div class="dn-img d-flex">
            <span class="position-relative w-100" style="display:block;">
              <img alt="Hình ảnh quỹ" class="img-fluid" src="${fund.imageUrl}">
            </span>
          </div>
          <div class="dn-body flex-grow-1 px-4 pb-3 pt-4">
            <div class="dn-title h5 font-weight-bold">${fund.name}</div>
          </div>
          <div class="dn-footer mb-4 px-4 pt-0">
            <div class="mb-3 d-flex align-items-center">
              <div class="flex-grow-1 text-xs text-muted">${fund.foundation.name}</div>
            </div>
            <div class="dn-detail">
              <div class="dn-money mb-2 d-flex align-items-end">
                <strong class="item-end d-flex align-items-center text-dark">${fund.totalDonations} đ/</strong>
                <span class="pl-2 text-xs text-muted">${fund.expectedResult} đ</span>
              </div>
              <div class="mt-3 d-flex justify-content-between align-items-center">
                <div class="flex-grow-1">
                  <div class="text-xs text-muted">Lượt quyên góp</div>
                  <div class="text-sm font-weight-bold text-dark">${fund.donationCount}</div>
                </div>
                <div class="flex-grow-1">
                  <div class="text-xs text-muted">Đạt được</div>
                  <div class="text-sm font-weight-bold text-dark">${fund.percentAchieved}%</div>
                </div>
                <div class="position-relative">
                  <a href="/Donations/detailFund?id=${fund.id}" class="btn btn-outline-info">Xem chi tiết</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </a>
    `;
        container.appendChild(card);
    });

    // Cập nhật trạng thái
    if (endIndex >= funds.filter(filterCallback).length) {
        loadMoreButton.classList.add("disabled");
        loadMoreButton.setAttribute("disabled", true);
    }

    container.setAttribute("data-current-page", currentPage + 1);
};

// Tải dữ liệu "Đang diễn ra"
ongoingLoadMore.addEventListener("click", () => {
    loadFunds(
        ongoingContainer,
        ongoingLoadMore,
        (fund) => fund.status === "Opening" && fund.percentAchieved < 100
    );
});

// Tải dữ liệu "Đã hoàn thành"
completedLoadMore.addEventListener("click", () => {
    loadFunds(
        completedContainer,
        completedLoadMore,
        (fund) => fund.status === "Finish"
    );
});

// Tải lần đầu khi trang mở
window.onload = () => {
    loadFunds(
        ongoingContainer,
        ongoingLoadMore,
        (fund) => fund.status === "Opening" && fund.percentAchieved < 100
    );
    loadFunds(
        completedContainer,
        completedLoadMore,
        (fund) => fund.status === "Finish"
    );
};
