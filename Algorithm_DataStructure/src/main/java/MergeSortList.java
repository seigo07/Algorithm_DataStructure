class MergeSortListCell {
    MergeSortListCell next;
    int data;

    MergeSortListCell(int data) {
        next = null;
        this.data = data;
    }
}

class MergeSortList {
    private static MergeSortListCell mergeList(MergeSortListCell a, MergeSortListCell b) {
        MergeSortListCell head = new MergeSortListCell(0);
        MergeSortListCell p = head;
        while (a != null && b != null) { // ①: aとbのどちらもnullでない間ループを続ける
            if (a.data < b.data) { // ②: aのデータがbのデータより小さいかどうかを比較
                p.next = a;
                p = a;
                a = a.next; // ③: ノードaを次に進める
            } else {
                p.next = b;
                p = b;
                b = b.next; // ④: ノードbを次に進める
            }
        }
        if (a == null) {
            p.next = b; // ⑤: aがnullならば、リストbの残りを追加する
        } else {
            p.next = a; // ⑥: bがnullならば、リストaの残りを追加する
        }
        return head.next;
    }

    public static MergeSortListCell mergeSortList(MergeSortListCell x) {
        if (x == null || x.next == null) {
            return x;
        }
        MergeSortListCell a = x;
        MergeSortListCell b = x.next;
        if (b != null) {
            b = b.next;
        }
        while (b != null) {
            a = a.next;
            b = b.next;
            if (b != null) {
                b = b.next;
            }
        }
        MergeSortListCell p = a.next; // ⑦: pをリストの中央であるa.nextに設定する
        a.next = null; // ⑧: aのnextをnullにしてリストを2つに分割する
        return mergeList(mergeSortList(x), mergeSortList(p));
    }
}
