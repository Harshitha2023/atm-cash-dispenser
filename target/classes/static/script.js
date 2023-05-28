$(document).ready(function() {
    // Retrieve and display the available cash notes
    $.ajax({
        url: '/cash/notes',
        method: 'GET',
        success: function(data) {
            var cashNotes = data;
            var tableBody = $('#cashNotesTable tbody');
            tableBody.empty();

            cashNotes.forEach(function(note) {
                var row = $('<tr>');
                row.append($('<td>').text(note.denomination));
                row.append($('<td>').text(note.quantity));
                tableBody.append(row);
            });
        }
    });

    // Handle the withdraw button click event
    $('#withdrawButton').click(function() {
        var amount = $('#amount').val();

        $.ajax({
            url: '/cash/withdraw/' + amount,
            method: 'POST',
            success: function(response) {
                if (response) {
                    alert('Cash dispensed successfully!');
                    location.reload();
                } else {
                    alert('Unable to dispense cash. Please try a different amount.');
                }
            }
        });
    });

    // Handle the initialize button click event
    $("#initializeForm").submit(function(event) {
        event.preventDefault();

        var twentyCount = parseInt($("#twenty").val());
        var fiftyCount = parseInt($("#fifty").val());

        // Send an AJAX request to the initialize endpoint
        $.ajax({
            url: "/cash/initialize",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify([
                { denomination: 20, quantity: twentyCount },
                { denomination: 50, quantity: fiftyCount }
            ]),
            success: function() {
                // Show success message
                alert("Cash notes initialized successfully.");
                location.reload();
            },
            error: function() {
                // Show error message
                $("#message").text("Failed to initialize cash notes.").css("color", "red");
            }
        });
    });

    // Handle the add button click event
    $('#addButton').click(function() {
        var addDenomination = $('#addDenomination').val();
        var addQuantity = $('#addQuantity').val();

        var addData = {
            denomination: addDenomination,
            quantity: addQuantity
        };

        $.ajax({
            url: '/cash/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(addData),
            success: function(response) {
                alert('Cash added successfully!');
                location.reload();
            }
        });
    });

    // Handle the remove button click event
    $('#removeButton').click(function() {
        var removeDenomination = $('#removeDenomination').val();
        var removeQuantity = $('#removeQuantity').val();

        var removeData = {
            denomination: removeDenomination,
            quantity: removeQuantity
        };

        $.ajax({
            url: '/cash/remove',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(removeData),
            success: function(response) {
                alert('Cash removed successfully!');
                location.reload();
            }
        });
    });
});
