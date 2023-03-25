document.addEventListener('DOMContentLoaded', () => {

    const getSort = ({ target }) => {
        const order = (target.dataset.order = -(target.dataset.order || -1));
        const index = [...target.parentNode.cells].indexOf(target);
        const collator = new Intl.Collator(['en', 'ru'], { numeric: true });
        const tableRowComparator = (index, order) => {
            return (rowA, rowB) => {
                const cellA = rowA.children[index].innerHTML;
                const cellB = rowB.children[index].innerHTML;

// will be false for integer numbers
                if (isNaN(cellA)) {
                    return order * collator.compare(cellA, cellB)
                }
                return order * (cellA - cellB);
            };
        };

        for(const tBody of target.closest('table').tBodies)
            tBody.append(...[...tBody.rows].sort(tableRowComparator(index, order)));

        for(const cell of target.parentNode.cells)
            cell.classList.toggle('sorted', cell === target);
    };

    document.querySelectorAll('.table_sort thead').forEach(tableTH => tableTH.addEventListener('click', () => getSort(event)));

});