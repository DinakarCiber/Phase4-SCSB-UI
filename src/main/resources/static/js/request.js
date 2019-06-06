/**
 * Created by rajeshbabuk on 25/10/16.
 */

$("a[href='https://htcrecap.atlassian.net/wiki/display/RTG/Search']").attr('href',
    'https://htcrecap.atlassian.net/wiki/display/RTG/Request');

function onChangeRequestStatus() {
    var status = $('#requestStatus').val();
    if (status == 'active'){
        $('#noteActive').show();
        $('#noteAll').hide();
    }
    else if (isBlankValue(status)){
        $('#noteAll').show();
        $('#noteActive').hide();
    }
    else {
        $('#noteAll').hide();
        $('#noteActive').hide();
    }
    $('#patronBarcodeSearchError').hide();
    $('#itemBarcodeSearchError').hide();
}

$(function() {
    $("#searchRequestsSection input").keypress(function (e) {
        if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
            $("#searchRequestsButton").click();
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            return false;
        } else {
            return true;
        }
    });
});

function loadCreateRequest() {
    var $form = $('#request-form');
    var url = $form.attr('action') + "?action=loadCreateRequest";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#requestContentId').html(response);
            $("#request .request-main-section").hide();
            $("#goBackLink").hide();
            $("#request .create-request-section").show();
        }
    });
}

function loadCreateRequestForSamePatron() {
    var patronBarcode = $("#patronBarcodeId").val();
    var patronEmailId = $("#patronEmailId").val();
    var requestingInstitutionId = $("#requestingInstitutionId").val();
    var $form = $('#request-form');
    var url = $form.attr('action') + "?action=loadCreateRequestForSamePatron";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#requestContentId').html(response);
            $("#request .request-main-section").hide();
            $("#goBackLink").hide();
            $("#request .create-request-section").show();
            $("#requestingInstitutionId option").prop("disabled", false);
            $('#patronBarcodeId').val(patronBarcode);
            $('#patronEmailId').val(patronEmailId);
            $('#requestingInstitutionId').val(requestingInstitutionId);
            $("#EDD").hide();
            $('#deliverylocation_request').show();
            $('#deliveryLocationId').empty();
        }
    });
}

function loadSearchRequest() {
    var $form = $('#request-form');
    var url = $form.attr('action') + "?action=loadSearchRequest";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#requestContentId').html(response);
            $("#request .request-main-section").show();
            $("#goBackLink").show();
            $("#request .create-request-section").hide();
        }
    });
}

function goToSearchRequest(patronBarcodeInRequest){
    var $form = $('#request-form');
    var url = "/request/goToSearchRequest";
    $.ajax({
        url: url,
        type: 'GET',
        data: {patronBarcodeInRequest: patronBarcodeInRequest},
        success: function (response) {
            $('#requestContentId').html(response);
            $("#goBackLink").show();
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            var status = $('#requestStatus').val();
            if (status == 'active'){
                $('#noteActive').show();
                $('#noteAll').hide();
            }
            else if (isBlankValue(status)){
                $('#noteAll').show();
                $('#noteActive').hide();
            }
            else {
                $('#noteAll').hide();
                $('#noteActive').hide();
            }
            var refreshStatus = statusChange();
            if(refreshStatus) {
                var interval = setInterval(statusChange,3000);
            }
        }
    });

}

function searchRequests(action) {
    var isValidSearch = validSearch();
    if (isValidSearch) {
        searchRequestsByAction(action);
    }
    else {
        $(".search-results-container").css('display', 'none');
    }
}

function validSearch() {
    var patronBarcode = $("#patronBarcode").val();
    var itemBarcode = $("#itemBarcode").val();
    var requestStatus = $("#requestStatus").val();
    var isValidSearch = true;
    if(isBlankValue(requestStatus)){
        if (isBlankValue(patronBarcode) && isBlankValue(itemBarcode)){
            isValidSearch = false;
            $('#patronBarcodeSearchError').show();
            $('#itemBarcodeSearchError').show();
        }
    }
    return isValidSearch;
}

function clearRequests() {
    $("#patronBarcode").val('');
    $("#itemBarcode").val('');
    $("#requestStatus").val('');
    $(".search-results-container").css('display', 'none');
    $('#patronBarcodeSearchError').hide();
    $('#itemBarcodeSearchError').hide();
    $('#noteAll').show();
    $('#noteActive').hide();
    $('#notesLengthErrMsg').hide();
}

function searchRequestsByAction(action) {
    var $form = $('#request-form');
    var url = $form.attr('action') + "?action=" + action;
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $('#searchRequestsSection').html(response);
            $("#request .request-main-section").show();
            $("#request .create-request-section").hide();
            var status = $('#requestStatus').val();
            if (status == 'active'){
                $('#noteActive').show();
                $('#noteAll').hide();
            }
            else if (isBlankValue(status)){
                $('#noteAll').show();
                $('#noteActive').hide();
            }
            else {
                $('#noteAll').hide();
                $('#noteActive').hide();
            }
            var refreshStatus = statusChange();
            if(refreshStatus) {
                var interval = setInterval(statusChange,3000);
            }
        }
    });
}

function statusChange(){
    var refreshStatus = false;
    var status = [];
    $("[name='statusChange']").each(function( ) {
        var statusVar = $( this ).val();
        status.push(statusVar);
    });
    if (status.length != 0){
        refreshStatus=true;
        $.ajax({
            url: "/request/refreshStatus",
            type: 'get',
            data: {status:status},
            success: function(response){
                var jsonResponse = JSON.parse(response);
                var changeStatus = jsonResponse['Status'];
                var changeNotes = jsonResponse['Notes'];
                if(changeStatus != null && changeStatus != '' && changeNotes != null && changeNotes != ''){
                    $.each(changeStatus, function (key, value) {
                        $("#status-" + key).html(value);
                        var reqStatus = value;
                        if(value =! "PROCESSING ..." ||  value != "PENDING") {
                            $('#refreshIcon-' + key).hide();
                            $('#removeName-' + key).removeAttr("name");
                        }
                        if(reqStatus == "RETRIEVAL ORDER PLACED" ||  reqStatus == "RECALL ORDER PLACED" || reqStatus == "EDD ORDER PLACED" || reqStatus == "LAS REFILE REQUEST PLACED") {
                            $('#showCancelButton-' + key).css("display", "block");}
                            $.each(changeNotes,function (key,value) {
                                $("#notes-" + key).val(value);
                            })
                    });
                }
            }
        });
    }
    return refreshStatus;
}

function requestsFirstPage() {
    searchRequestsByAction('first');
}

function requestsLastPage() {
    searchRequestsByAction('last');
}

function requestsPreviousPage() {
    $('#pageNumber').val(parseInt($('#pageNumber').val()) - 1);
    searchRequestsByAction('previous');
}

function requestsNextPage() {
    $('#pageNumber').val(parseInt($('#pageNumber').val()) + 1);
    searchRequestsByAction('next');
}

function requestsOnPageSizeChange() {
    searchRequestsByAction('requestPageSizeChange');
}

function populateItemDetails() {
    var itemBarcode = $('#itemBarcodeId').val();
    if (!isBlankValue(itemBarcode)) {
        var $form = $('#request-form');
        var url = $form.attr('action') + "?action=populateItem";
        $.ajax({
            url: url,
            type: 'post',
            data: $form.serialize(),
            success: function (response) {
                var jsonResponse = JSON.parse(response);
                $('#itemTitleId').val(jsonResponse['itemTitle']);
                $('#itemOwningInstitutionId').val(jsonResponse['itemOwningInstitution']);
                var errorMessage = jsonResponse['errorMessage'];
                var notAvailableErrorMessage = jsonResponse['notAvailableErrorMessage'];
                var noPermissionErrorMessage = jsonResponse['noPermissionErrorMessage'];
                var deliveryLocation = jsonResponse['deliveryLocation'];
                var requestTypes = jsonResponse['requestTypes'];
                $('#requestTypeId').empty();
                $.each(requestTypes,function (index,value) {
                    $('#requestTypeId').append($("<option/>", {
                        value: value,
                        text: value
                    }));
                });
                if (deliveryLocation != null && deliveryLocation != '') {
                    $('#deliveryLocationId').empty();
                    $('#deliveryLocationId').append($("<option/>", {
                        value: "",
                        text: ""
                    }));
                    $.each(deliveryLocation, function (key, value) {
                        $('#deliveryLocationId').append($("<option/>", {
                            value: key,
                            text: value + "-" +key
                        }));
                    });
                }
                $("#EDD").hide();
                $('#deliverylocation_request').show();
                $('#deliveryLocationId').val("");
                $('#emailMandatory').hide();
                $('#itemBarcodeErrorMessage').hide();
                if (errorMessage != null && errorMessage != '' && notAvailableErrorMessage != null && notAvailableErrorMessage != '') {
                    $('#itemBarcodeNotFoundErrorMessage').html(errorMessage + "<br>" + notAvailableErrorMessage);
                    $('#itemBarcodeNotFoundErrorMessage').show();
                } else if ((errorMessage != null && errorMessage != '')) {
                    $('#itemBarcodeNotFoundErrorMessage').html(errorMessage);
                    $('#itemBarcodeNotFoundErrorMessage').show();
                } else if ((notAvailableErrorMessage != null && notAvailableErrorMessage != '')) {
                    $('#itemBarcodeNotFoundErrorMessage').html(notAvailableErrorMessage);
                    $('#itemBarcodeNotFoundErrorMessage').show();
                } else if (noPermissionErrorMessage != null && noPermissionErrorMessage != '') {
                    $('#itemBarcodeNotFoundErrorMessage').html(noPermissionErrorMessage);
                    $('#itemBarcodeNotFoundErrorMessage').show();
                } else {
                    $('#itemBarcodeNotFoundErrorMessage').html('');
                }
            }
        });
    }
}

function isValidInputs() {
    var isValid = true;

    var itemBarcode = $('#itemBarcodeId').val();
    var patronBarcode = $('#patronBarcodeId').val();
    var requestType = $('#requestTypeId').val();
    var deliveryLocation = $('#deliveryLocationId').val();
    var requestingInstitution = $('#requestingInstitutionId').val();
    var notesLength = $('#requestNotesId').val().length;
    if (notesLength  == 1000){
        $('#notesLengthErrMsg').show();
    }else {
        $('#notesLengthErrMsg').hide();
    }
    validateEmailAddress();

    if (isBlankValue(itemBarcode)) {
        $('#itemBarcodeErrorMessage').show();
        isValid = false;
    } else {
        $('#itemBarcodeErrorMessage').hide();
    }
    if (isBlankValue(patronBarcode)) {
        $('#patronBarcodeErrorMessage').show();
        isValid = false;
    } else {
        $('#patronBarcodeErrorMessage').hide();
    }
    if (isBlankValue(requestType)) {
        $('#requestTypeErrorMessage').show();
        isValid = false;
    } else {
        if (requestType == 'EDD') {
            var startPage = $('#StartPage').val();
            var endPage = $('#EndPage').val();
            var articleTitle = $('#ArticleChapterTitle').val();
            var patronEmailId = $('#patronEmailId').val();

            if (isBlankValue(startPage)) {
                $('#startPageErrorMessage').show();
                isValid = false;
            } else {
                $('#startPageErrorMessage').hide();
            }
            if (isBlankValue(endPage)) {
                $('#endPageErrorMessage').show();
                isValid = false;
            } else {
                $('#endPageErrorMessage').hide();
            }
            if (isBlankValue(articleTitle)) {
                $('#articleTitleErrorMessage').show();
                isValid = false;
            } else {
                $('#articleTitleErrorMessage').hide();
            }
            if(isBlankValue(patronEmailId)){
                $('#EmailMandatoryErrorMessage').show();
                isValid = false;
            }
            else {
                $('#EmailMandatoryErrorMessage').hide();
            }
        }
        $('#requestTypeErrorMessage').hide();
    }
    if (isBlankValue(deliveryLocation)) {
        if (!(requestType == 'EDD')) {
            $('#deliveryLocationErrorMessage').show();
            isValid = false;
        }
    } else {
        $('#deliveryLocationErrorMessage').hide();
    }
    if (isBlankValue(requestingInstitution)) {
        $('#requestingInstitutionErrorMessage').show();
        isValid = false;
    } else {
        $('#requestingInstitutionErrorMessage').hide();
    }
    return isValid;
}

function createRequest() {
    var requestType = $('#requestTypeId').val();
    if (isValidInputs()) {
        var $form = $('#request-form');
        var url = $form.attr('action') + "?action=createRequest";
        $.ajax({
            url: url,
            type: 'post',
            data: $form.serialize(),
            beforeSend: function () {
                $('#createRequestSection').block({
                    message: '<h1>Processing...</h1>'
                });
            },
            success: function (response) {
                if(requestType == 'EDD'){
                    $('#createRequestSection').unblock();
                    $('#createRequestSection').html(response);
                    $("#textField").hide();
                    $("#requestNotesRemainingCharacters").hide();
                    $("#emailMandatory").show();
                    $('#deliverylocation_request').hide();
                    $("#EDD").css("display", "");
                }
                else {
                    $('#createRequestSection').unblock();
                    $('#createRequestSection').html(response);
                    $("#textField").hide();
                    $("#requestNotesRemainingCharacters").hide();
                }
            }
        });
    }
}

function isBlankValue(value) {
    if (value == null || value == '') {
        return true;
    }
    return false;
}

function resetDefaults() {
    $('#errorMessageId').hide();
    $('#itemBarcodeErrorMessage').hide();
    $('#patronBarcodeErrorMessage').hide();
    $('#requestTypeErrorMessage').hide();
    $('#deliveryLocationErrorMessage').hide();
    $('#requestingInstitutionErrorMessage').hide();
    $('#startPageErrorMessage').hide();
    $('#endPageErrorMessage').hide();
    $('#articleTitleErrorMessage').hide();
    $('#patronEmailIdErrorMessage').hide();
    $('#itemBarcodeNotFoundErrorMessage').hide();
    $('#itemBarcodeId').val('');
    $('#itemTitleId').val('');
    $('#itemOwningInstitutionId').val('');
    $('#patronBarcodeId').val('');
    $('#patronEmailId').val('');
    $('#requestTypeId').val('RETRIEVAL');
    $('#deliveryLocationId').val('');
    var length = $('select#requestingInstitutionId option').length;
    if(length>2){
        $('#requestingInstitutionId').val('');
    }
    $('#requestNotesId').val('');
    $('#deliverylocation_request').show();
    $('#deliveryLocationId').empty();
    //EDD
    $('#EDD').hide();
    $('#StartPage').val('');
    $('#EndPage').val('');
    $('#VolumeNumber').val('');
    $('#Issue').val('');
    $('#ArticleAuthor').val('');
    $('#ArticleChapterTitle').val('');
    $('#EmailMandatoryErrorMessage').hide();
    $('#emailMandatory').hide();
    $('#notesLengthErrMsg').hide();


}

function toggleItemBarcodeValidation() {
    var itemBarcode = $('#itemBarcodeId').val();
    if (isBlankValue(itemBarcode) && !isBlankValue(itemBarcode)) {
        $('#itemBarcodeErrorMessage').show();
        $('#itemBarcodeNotFoundErrorMessage').hide();
        $('#itemTitleId').val('');
        $('#itemOwningInstitutionId').val('');
        $('#patronBarcodeId').val('');
        $('#patronEmailId').val('');
        $('#deliveryLocationId').val('');
        $('#deliveryLocationId').empty();
    } else {
        $('#itemBarcodeErrorMessage').hide();
    }
}

function toggleRequestingInstitutionValidation() {
    var requestingInstitution = $('#requestingInstitutionId').val();
    if (isBlankValue(requestingInstitution)) {
        $('#requestingInstitutionErrorMessage').show();
    } else {
        $('#requestingInstitutionErrorMessage').hide();
    }
}

function togglePatronBarcodeValidation() {
    var patronBarcode = $('#patronBarcodeId').val();
    if (isBlankValue(patronBarcode) && !isBlankValue(patronBarcode)) {
        $('#patronBarcodeErrorMessage').show();
    } else {
        $('#patronBarcodeErrorMessage').hide();
    }
}

function toggleDeliveryLocationValidation() {
    var deliveryLocation = $('#deliveryLocationId').val();
    if (isBlankValue(deliveryLocation)) {
        $('#deliveryLocationErrorMessage').show();
    } else {
        $('#deliveryLocationErrorMessage').hide();
    }
}

function toggleStartPageValidation() {
    var startPage = $('#StartPage').val();
    if (isBlankValue(startPage) && !isBlankValue(startPage)) {
        $('#startPageErrorMessage').show();
    } else {
        $('#startPageErrorMessage').hide();
    }
}

function toggleEndPageValidation() {
    var endPage = $('#EndPage').val();
    if (isBlankValue(endPage) && !isBlankValue(endPage)) {
        $('#endPageErrorMessage').show();
    } else {
        $('#endPageErrorMessage').hide();
    }
}

function toggleArticleTitleValidation() {
    var articleTitle = $('#ArticleChapterTitle').val();
    if (isBlankValue(articleTitle) && !isBlankValue(articleTitle)) {
        $('#articleTitleErrorMessage').show();
    } else {
        $('#articleTitleErrorMessage').hide();
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

function createRequestSamePatron() {
    $('#createRequestModal').modal('hide');
    $("#requestingInstitutionId option").prop("disabled", false);
    $('#patronBarcodeId').val($('#patronBarcodeInRequest').html());
    $('#patronEmailId').val($('#patronEmailAddress').html());
    $('#requestingInstitutionId').val($('#requestingInstitution').html());
    $("#EDD").hide();
    $('#deliverylocation_request').show();
    $('#deliveryLocationId').empty();
}

function cancelRequest(index) {
    var requestId = $("#requestRowRequestId-" + index).val();
    $("#requestIdHdn").val(requestId);
    $('#cancelConfirmationModal').modal('show');
    $("#cancelConfirmIndexId").val(index);
    $("#cancelConfirmRequestId").val(requestId);
}

function cancelRequestItem() {
    $('#cancelConfirmationModal').modal('hide');
    var index = $("#cancelConfirmIndexId").val();
    var $form = $('#request-form');
    var url = $form.attr('action') + "?action=cancelRequest";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        beforeSend: function () {
            $('#searchRequestsSection').block({
                message: '<h1>Processing...</h1>'
            });
        },
        success: function (response) {
            $('#searchRequestsSection').unblock();
            var jsonResponse = JSON.parse(response);
            var message = jsonResponse['Message'];
            var status = jsonResponse['Status'];
            var requestStatus = jsonResponse['RequestStatus'];
            var requestNotes = jsonResponse['requestNotes'];
            if (status) {
                $("#cancelStatus").html("Request canceled successfully");
                $("#status-" + index).html(requestStatus);
                $("#notes-" + index).val(requestNotes);
                $("#cancelButton-" + index).hide();
                $("#showCancelButton-" + index).hide();
            } else {
                $("#cancelStatus").html("Request cancellation failed. " + message);
            }
            $('#cancelRequestModal').modal('show');
        }
    });
}

function resubmitRequest(index) {
    $("#requestSelectedIndexHdn").val(index);
    var requestId = $("#requestRowRequestId-" + index).val();
    var itemBarcode = $("#requestRowItemBarcode-" + index).val();
    $("#requestIdHdn").val(requestId);
    $("#requestItemBarcodeHdn").val(itemBarcode);
    $("#resubmitReqConfirmItemBarcode").html(itemBarcode);
    $("#resubmitRequestBodyId").hide();
    $('#resubmitRequestModal').modal('show');
}

function resubmitRequestItem(index) {
    var $form = $('#request-form');
    var url = $form.attr('action') + "?action=resubmitRequest";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        beforeSend: function () {
            $('#resubmitRequestModal').block({
                message: '<h1>Processing...</h1>'
            });
        },
        success: function (response) {
            $('#resubmitRequestModal').unblock();
            var jsonResponse = JSON.parse(response);
            var itemBarcode = jsonResponse['Barcode'];
            var message = jsonResponse['Message'];
            var status = jsonResponse['Status'];
            $("#resubmitReqItemBarcode").html(itemBarcode);
            $("#resubmitRequestStatus").html(message);
            if (status) {
                $("#resubmitRequestStatus").removeClass('text-danger');
                $("#resubmitRequestStatus").addClass('text-success');
            } else {
                $("#resubmitRequestStatus").removeClass('text-success');
                $("#resubmitRequestStatus").addClass('text-danger');
            }
            $("#resubmitRequestConfirmBodyId").hide();
            $("#resubmitRequestConfirmFooterId").hide();
            $("#resubmitRequestBodyId").show();
            $("#resubmitButton-" + $("#requestSelectedIndexHdn").val()).prop("disabled", status);
        }
    });
}

function closeResubmitRequestItem() {
    $('#closeResubmitRequest').click();
}

function resetToResubmitRequest() {
    $("#resubmitRequestConfirmBodyId").show();
    $("#resubmitRequestConfirmFooterId").show();
    $("#resubmitRequestBodyId").hide();
}

function showNotesPopup(index) {
    var notes = $("#notes-" + index).val();
    $("#requestNotesData").html(notes);
    $('#requestNotesModal').modal('show');
}

function togglePatronBarcodeSearch(){
    var patronBarcode = $('#patronBarcode').val();
    if(isBlankValue(patronBarcode) && !isBlankValue(patronBarcode)){
        $('#patronBarcodeSearchError').show();
    }
    else{
        $('#patronBarcodeSearchError').hide();
    }
}

function toggleItemBarcodeSearch(){
    var itemBarcode = $('#itemBarcode').val();
    if(isBlankValue(itemBarcode) && !isBlankValue(itemBarcode)){
        $('#itemBarcodeSearchError').show();
    }
    else{
        $('#itemBarcodeSearchError').hide();
    }
}

function toggleEmailAddress(){
    var requestType = $('#requestTypeId').val();
    if(requestType == 'EDD') {
        var patronEmailId = $('#patronEmailId').val();
        if (isBlankValue(patronEmailId) && !isBlankValue(patronEmailId)) {
            $('#patronEmailIdErrorMessage').hide();
            $('#EmailMandatoryErrorMessage').show();
        }
        else {
            $('#EmailMandatoryErrorMessage').hide();
        }
    }
}

function emailMandatory(){
    var requestType = $('#requestTypeId').val();
    $("#EDD").hide();
    $("#EDD").css("display","none");
    if (requestType === 'EDD') {
        $("#EDD").css("display","");
        $('#deliverylocation_request').hide();
        $('#deliveryLocationId').val("");
        $('#emailMandatory').show();
    } else {
        $('#deliverylocation_request').show();
        $('#emailMandatory').hide();
        $('#EmailMandatoryErrorMessage').hide();
    }
}


function populateDeliveryLocations(){
    var requestingInstitutionId = $('#requestingInstitutionId').val();
    if(!isBlankValue(requestingInstitutionId)){
        toggleRequestingInstitutionValidation();
    }
    $('#onChangeOwnInst').val('true');
    var $form = $('#request-form');
    var url = $form.attr('action') + "?action=populateItem";
    $.ajax({
        url: url,
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            var jsonResponse = JSON.parse(response);
            $('#itemTitleId').val(jsonResponse['itemTitle']);
            $('#itemOwningInstitutionId').val(jsonResponse['itemOwningInstitution']);
            var errorMessage = jsonResponse['errorMessage'];
            var noPermissionErrorMessage = jsonResponse['noPermissionErrorMessage'];
            var deliveryLocation = jsonResponse['deliveryLocation'];
            if (deliveryLocation != null && deliveryLocation != '') {
                $('#deliveryLocationId').empty();
                $('#deliveryLocationId').append($("<option/>", {
                    value: "",
                    text: ""
                }));
                $.each(deliveryLocation, function (key, value) {
                    $('#deliveryLocationId').append($("<option/>", {
                        value: key,
                        text: value + "-" +key
                    }));
                });
            }
            $('#itemBarcodeErrorMessage').hide();
            if (errorMessage != null && errorMessage != '') {
                $('#itemBarcodeNotFoundErrorMessage').html(errorMessage);
                $('#itemBarcodeNotFoundErrorMessage').show();
            } else if (noPermissionErrorMessage != null && noPermissionErrorMessage != '') {
                $('#itemBarcodeNotFoundErrorMessage').html(noPermissionErrorMessage);
                $('#itemBarcodeNotFoundErrorMessage').show();
            } else {
                $('#itemBarcodeNotFoundErrorMessage').html('');
            }
        }
    });
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