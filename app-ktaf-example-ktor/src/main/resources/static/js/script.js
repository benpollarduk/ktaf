const commandInput = document.getElementById('command');
const form = document.getElementById('inputForm');

// enter trigger submit
commandInput.addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        form.submit();
    }
});

// wait for the page to fully load then give focus to input
window.addEventListener('load', function() {
    const inputElement = document.getElementById('command');
    inputElement.focus();
});