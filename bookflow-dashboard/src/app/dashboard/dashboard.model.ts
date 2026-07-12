export interface DashboardResponse {
  memberId: number;
  items: DashboardItem[];
}

export interface DashboardItem {
  bookId: number;
  title: string;
  author: string;
  borrowedAt: string;
  myRating: number | null;
  averageRating: number | null;
}