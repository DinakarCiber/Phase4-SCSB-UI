

    $("a[href='https://htcrecap.atlassian.net/wiki/display/RTG/Search']").attr('href',
        'https://htcrecap.atlassian.net/wiki/display/RTG/Bulk+Request');

function loadCreateRequest(){
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/loadCreateRequest";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#requestContentId').html(response);
        }
    });
}

function loadSearchRequest(){
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/loadSearchRequest";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#requestContentId').html(response);
            $('#searchRequestsSection').show();
            $('#createRequestSection').hide();
            $('#goBackLink').show();
        }
    });
}


function searchRequests(){
    if (isValidSearchInput()){
        var $form = $('#bulkRequest-form');
        var url = "/bulkRequest/searchRequest";
        $.ajax({
            url: url,
            type: 'post',
            data: $form.serialize(),
            success: function (response) {
                $('#searchRequestsSection').html(response);
                $("#request .request-main-section").show();
                $("#request .create-request-section").hide();
                $('#goBackLink').show();
            }
        });
    }

}

function isValidSearchInput(){
    var valid = true;
    var bulkRequestId = $('#bulkRequestIdSearch').val();
    var regexp=new RegExp("[^0-9]");
    if(regexp.test(bulkRequestId)) {
        $('#bulkRequestIdSearchError').show();
        valid = false;
    }
    return valid;
}

function requestsOnPageSizeChange(){
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/onPageSizeChange";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#searchRequestsSection').html(response);
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            $('#goBackLink').show();
        }
    });
}

function requestsFirstPage(){
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/searchFirst";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#searchRequestsSection').html(response);
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            $('#goBackLink').show();
        }
    });

}

function requestsPreviousPage(){
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/searchPrevious";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#searchRequestsSection').html(response);
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            $('#goBackLink').show();
        }
    });

}

function requestsNextPage(){
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/searchNext";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#searchRequestsSection').html(response);
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            $('#goBackLink').show();
        }
    });

}

function requestsLastPage(){
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/searchLast";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#searchRequestsSection').html(response);
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            $('#goBackLink').show();
        }
    });

}

function showNotesPopup(index) {
    var notes = $("#notes-" + index).val();
    $("#requestNotesData").html(notes);
    $('#requestNotesModal').modal('show');
}

function goToSearchRequest(patronBarcodeInRequest){
    var $form = $('#request-form');
    var url = "/bulkRequest/goToSearchRequest";
    $.ajax({
        url: url,
        type: 'GET',
        data: {patronBarcodeInRequest: patronBarcodeInRequest},
        success: function (response) {
            $('#requestContentId').html(response);
            $("#goBackLink").show();
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
        }
    });

}

function loadCreateRequestForSamePatron(){
    var patronBarcode = $("#patronBarcodeId").val();
    var patronEmailId = $("#patronEmailId").val();
    var requestingInstitutionId = $("#requestingInstitutionId").val();
    $("#reqInstituionHidden").val(requestingInstitutionId);
    var $form = $('#bulkRequest-form');
    var url = "/bulkRequest/loadCreateRequestForSamePatron";
    $.ajax({
        url: url,
        type: 'post',
        data :$form.serialize(),
        success: function (response) {
            $('#requestContentId').html(response);
            $("#patronBarcodeId").val(patronBarcode);
            $("#patronEmailId").val(patronEmailId);
            $("#requestingInstitutionId").val(requestingInstitutionId);
            $("#file").filestyle('clear');
        }
    });

}

function resetDefaults(){
    $("#patronBarcodeId").val('');
    $("#patronEmailId").val('');
    $("#requestingInstitutionId").val('');
    $("#BulkRequestName").val('');
    $("#deliveryLocation").val('');
    $("#requestNotesId").val('');
    $('#requestingInstitutionErrorMessage').hide();
    $('#patronBarcodeErrorMessage').hide();
    $('#patronEmailIdErrorMessage').hide();
    $('#bulkRequestFileRequired').hide();
    $('#invalidBulkRequestFile').hide();
    $('#BulkRequestNameErrorMessage').hide();
    $("#file").filestyle('clear');
    $('#errorMessageId1').hide();
    $('#BulkRequestNameLengthError').hide();
    $('#BulkRequestNameErrorMessage').hide();
    $('#deliveryLocationErrorMessage').hide();
}

function createBulkRequest() {
    if (isValidInputs()) {
        var $form = $('#bulkRequest-form');
        var form = $('#bulkRequest-form')[0];
        var data = new FormData(form);
        var url = "/bulkRequest/createBulkRequest";
        $.ajax({
            url: url,
            type: 'post',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            data: data,
            beforeSend: function () {
                $('#createRequestSection').block({
                    message: '<h1>Processing...</h1>'
                });
            },
            success: function (response) {
                $('#createRequestSection').unblock();
                $('#requestContentId').html(response);
                $('#requestNotesRemainingCharacters').hide();
                $("#textField").hide();
            }
        });
    }
}

function isValidInputs() {
    var isValid = true;

    var bulkRequestName = $('#BulkRequestName').val();
    var patronBarcode = $('#patronBarcodeId').val();
    var patronEmailId = $('#patronEmailId').val();
    var isValidEmailAddress = $('#patronEmailId').is(':valid');
    var requestingInstitution = $('#requestingInstitutionId').val();
    var file = $('#file').val();
    var fileExtension = $('#file').val().split('.').pop().toLowerCase();


    var deliveryLocation = $('#deliveryLocation').val();


    if (isBlankValue(bulkRequestName)) {
        $('#BulkRequestNameErrorMessage').show();
        isValid = false;
    } else {
        if (bulkRequestName.length > 255){
            isValid=false;
            $('#BulkRequestNameLengthError').show();
            $('#BulkRequestNameErrorMessage').hide();

        }else {
            $('#BulkRequestNameLengthError').hide();
            $('#BulkRequestNameErrorMessage').hide();
        }
    }
    if (isBlankValue(patronBarcode)) {
        $('#patronBarcodeErrorMessage').show();
        isValid = false;
    } else {
        $('#patronBarcodeErrorMessage').hide();
    }
    if (!isBlankValue(patronEmailId)){
        if (!isValidEmailAddress) {
            $('#patronEmailIdErrorMessage').show();
            isValid = false;
        } else {
            $('#patronEmailIdErrorMessage').hide();
        }
    }
    if (isBlankValue(requestingInstitution)){
        $('#requestingInstitutionErrorMessage').show();
        isValid = false;
    }else {
        $('#requestingInstitutionErrorMessage').hide();
    }
    if(!isBlankValue(file)){
        if (fileExtension != 'csv'){
            $('#invalidBulkRequestFile').show();
            isValid = false;
        }else {
            $('#invalidBulkRequestFile').hide();
        }
    } else {
        $('#bulkRequestFileRequired').show();
        isValid = false;
    }
    if(isBlankValue(deliveryLocation)){
        $('#deliveryLocationErrorMessage').show();
        isValid=false;
    }else {
        $('#deliveryLocationErrorMessage').hide();
    }
    return isValid;
}

function isBlankValue(value) {
    if (value == null || value == '') {
        return true;
    }
    return false;
}

function toggleBulkRequestNameValidation(){
    $('#BulkRequestNameErrorMessage').hide();
    $('#BulkRequestNameLengthError').hide();
}

function toggleFileValidation(){
    var file = $('#file').val();
    var fileExtension = $('#file').val().split('.').pop().toLowerCase();
    if(!isBlankValue(file)){
        if (fileExtension != 'csv'){
            $('#invalidBulkRequestFile').show();
            $('#bulkRequestFileRequired').hide();
        }else {
            $('#invalidBulkRequestFile').hide();
            $('#bulkRequestFileRequired').hide();
        }
    } else {
        $('#bulkRequestFileRequired').show();
        $('#invalidBulkRequestFile').hide();
    }

}

function validateEmailAddress() {
    var isValidEmailAddress = $('#patronEmailId').is(':valid');
    if (!isValidEmailAddress) {
        $('#patronEmailIdErrorMessage').show();
    } else {
        $('#patronEmailIdErrorMessage').hide();
    }
}

function togglePatronBarcodeValidation(){
    $('#patronBarcodeErrorMessage').hide();
}

function populateDeliveryLocations(){
    var requestingInstitution = $('#requestingInstitutionId').val();
    if (!isBlankValue(requestingInstitution)){
        $('#requestingInstitutionErrorMessage').hide();
        var $form = $('#bulkRequest-form');
        var url = "/bulkRequest/populateDeliveryLocations";
        $.ajax({
            url: url,
            type: 'post',
            data :$form.serialize(),
            success: function (response) {
                $('#deliveryLocation').html(response);
            }
        });
    }
}

function NotesLengthValidation(val){
    val.style.height = "1px";
    val.style.height = (25+val.scrollHeight)+"px";
    var len = val.value.length;
    if (len > 1000) {
        val.value = val.value.substring(0, 1000);
    } else {
        $('#remainingCharacters').text(1000 - len);
    }
    var notesLength = $('#requestNotesId').val().length;
    if (notesLength == 1000){
        $('#notesLengthErrMsg').show();
    }else {
        $('#notesLengthErrMsg').hide();
    }
}

function toggleBulkRequestIdSearch(){
    $('#bulkRequestIdSearchError').hide();
}

function toggleDeliveryLocationValidation(){
    $('#deliveryLocationErrorMessage').hide();
}