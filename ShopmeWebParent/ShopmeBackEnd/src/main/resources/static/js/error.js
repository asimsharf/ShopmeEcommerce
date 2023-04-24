function randomNum() {
    "use strict";
    return Math.floor(Math.random() * 9) + 1;
}

var loop1, loop2, loop3,
    time = 30, i = 0, number,

    selector9 = document.querySelector('.ninthDigit'),
    selector8 = document.querySelector('.eighthDigit'),
    selector7 = document.querySelector('.seventhDigit'),

    selector6 = document.querySelector('.sixthDigit'),
    selector5 = document.querySelector('.fifthDigit'),
    selector4 = document.querySelector('.fourthDigit'),

    selector3 = document.querySelector('.thirdDigit'),
    selector2 = document.querySelector('.secondDigit'),
    selector1 = document.querySelector('.firstDigit');

loop3 = setInterval(function () {
    "use strict";
    if (selector3 != null) {
        if (i > 40) {
            clearInterval(loop3);
            selector3.textContent = 4;
        } else {
            selector3.textContent = randomNum();
            i++;
        }
    }
}, time);

loop2 = setInterval(function () {
    "use strict";
    if (selector2 != null) {
        if (i > 80) {
            clearInterval(loop2);
            selector2.textContent = 0;
        } else {
            selector2.textContent = randomNum();
            i++;
        }
    }
}, time);

loop1 = setInterval(function () {
    "use strict";
    if (selector1 != null) {
        if (i > 100) {
            clearInterval(loop1);
            selector1.textContent = 4;
        } else {
            selector1.textContent = randomNum();
            i++;
        }
    }
}, time);

loop4 = setInterval(function () {
    "use strict";
    if (selector4 != null) {
        if (i > 40) {
            clearInterval(loop4);
            selector4.textContent = 3;
        } else {
            selector4.textContent = randomNum();
            i++;
        }
    }
}, time);

loop5 = setInterval(function () {
    "use strict";
    if (selector5 != null) {
        if (i > 80) {
            clearInterval(loop5);
            selector5.textContent = 0;
        } else {
            selector5.textContent = randomNum();
            i++;
        }
    }
}, time);

loop6 = setInterval(function () {
    "use strict";
    if (selector6 != null) {
        if (i > 100) {
            clearInterval(loop6);
            selector6.textContent = 4;
        } else {
            selector6.textContent = randomNum();
            i++;
        }
    }
}, time);

loop7 = setInterval(function () {
    "use strict";
    if (selector7 != null) {
        if (i > 40) {
            clearInterval(loop7);
            selector7.textContent = 0;
        } else {
            selector7.textContent = randomNum();
            i++;
        }
    }
}, time);

loop8 = setInterval(function () {
    "use strict";
    if (selector8 != null) {
        if (i > 80) {
            clearInterval(loop8);
            selector8.textContent = 0;
        } else {
            selector8.textContent = randomNum();
            i++;
        }
    }
}, time);

loop9 = setInterval(function () {
    "use strict";
    if (selector9 != null) {
        if (i > 100) {
            clearInterval(loop9);
            selector9.textContent = 5;
        } else {
            selector9.textContent = randomNum();
            i++;
        }
    }
}, time);

