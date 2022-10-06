import { LitElement, html, property } from "lit-element";

const geoOptions = {
  enableHighAccuracy: false,
  timeout: 5000,
  maximumAge: 0
};

class GeoLocation extends LitElement {

    @property( { type : Number }  ) latitude = 1.0;
    @property( { type : Number }  ) longitude = 1.0;
    private $server?: GeoLocationServerInterface;

    render() {
        return html`<p>Test!</p>`;
    }

    private _geoSuccess(position: GeolocationPosition) {
        // Set attribute
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        //this.longitude = position.coords.longitude;
        this.$server!.onUpdate();
    }

    private _geoError(error: any) {
        // Set attribute

        this.$server!.onError();
    }

    updateLocation() {
        navigator.geolocation.getCurrentPosition((geo) => this._geoSuccess(geo), (err) => this._geoError(err), geoOptions);
    }



    constructor() {
        super();
        var id = navigator.geolocation.watchPosition((geo) => this._geoSuccess(geo), (err) => this._geoError(err), geoOptions);
        this.latitude = 0.0;
        this.longitude = 0.0;
    }
}

interface GeoLocationServerInterface {
    onUpdate(): void;
    onError(): void;
}

customElements.define("geoloc-custom", GeoLocation);
