import CommonChatController from '../../../../app/assets/javascripts/ci_api/controllers/CommonChatController'
import ProactiveChatController from '../../../../app/assets/javascripts/ci_api/controllers/ProactiveChatController'

describe("ProactiveChatController", () => {
    it("launches a proactive chat", () => {
        const commonChatController = new CommonChatController();
        const proactiveChatController = new ProactiveChatController();

        const sdk = {
            isChatInProgress: jest.fn().mockReturnValue(false),
            getOpenerScripts: jest.fn().mockReturnValue(null),
            chatDisplayed: jest.fn()
        };

        window.Inq = {
            SDK: sdk
        };

        commonChatController.nuanceFrameworkLoaded(window);
        proactiveChatController.launchProactiveChat();

        expect(sdk.getOpenerScripts).toHaveBeenCalled();
        expect(sdk.chatDisplayed).toHaveBeenCalled();
    })

    it("appends chat transcript div to page when no div id is found on page", () => {
        const commonChatController = new CommonChatController();

        commonChatController._showChat();

        expect(document.getElementById("ciapiSkinChatTranscript").innerHTML).not.toBe(null);

    });

    it("appends chat transcript div to page when some div id is found on page", () => {
        const commonChatController = new CommonChatController();
        let chatContainer = document.createElement("div");
        chatContainer.setAttribute("id", "HMRC_CIAPI_Fixed_1");
        document.body.appendChild(chatContainer);

        commonChatController._showChat();

        expect(document.getElementById("ciapiSkinChatTranscript").innerHTML).not.toBe(null);

    });
});