//record-klasse brukes bare for lagring av data som videre skal importeres videre til database
//Data her kan ikke endres, økt sikkerhet med integritet
public record Scrapyard(int scrapyardId, String name, String address, String phoneNumber) {
}
